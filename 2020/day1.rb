#!/usr/bin/env ruby

=begin
Day one we started with an list of numbers,
we had to add all of the numbers in the list
and if they added to 2020 we would times together
=end

# load input into array
# we can't just iterate on the file as
# the position in the file is global
lines = IO.readlines("input/day1-input")

# we loop through the array
lines.each do |i|
  # and then for all of the indexs in the array..
  # we loop again!
  lines.each do |j| 
    # now we do the adding
    x = i.to_i + j.to_i
    if x == 2020
      p i.to_i * j.to_i
      break
    end
  end
end

# we needed to do the same thing but 3 times...
# so we just add in one more loop!
# its not fast but for 200 numbers its not too much of a wait
lines.each do |i|
  lines.each do |j|
    lines.each do |k|
      if i.to_i + j.to_i + k.to_i == 2020
        p i.to_i * j.to_i * k.to_i
      end
    end
  end
end
# I think its a big O of.. well lets calculate it!
=begin
it takes array.len times to loop through one array
we loop through an array for every number in array
so for this probem it would be it would be N^3 ?
=end

# now can we do it for n?
# probably but
