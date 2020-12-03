#!/usr/bin/env ruby

lines = IO.readlines("input/day2-input")

# clean and first split
lines.each_with_index do |line ,i|
  lines[i] = line.sub!("\n", "")
  lines[i] = line.split(":")
end

#split the range into 2 sections
lines.each_with_index do |line, i| 
    lines[i][0] = lines[i][0].split(" ")
end

lines.each_with_index do |line ,i| 
  # spliting into min and max
  lines[i][0][0] = lines[i][0][0].split("-")
  #converting to ints
  lines[i][0][0][0] = lines[i][0][0][0].to_i
  lines[i][0][0][1] = lines[i][0][0][1].to_i
end
# part 1
# now we begin checkings
w = 0
lines.each_with_index do |line, i|
  min = lines[i][0][0][0]
  max = lines[i][0][0][1]
  current = lines[i][1].count(lines[i][0][1])
  if current >= min
    if current <= max
        w += 1
    end
  end 
end
print w ,"w", "\n"

# part 2
x = 0
lines.each_with_index do |line, i| 
  first  = lines[i][0][0][0]
  last    = lines[i][0][0][1]
  current  = lines[i][1]
  check     = lines[i][0][1]
  if current[first] == check && current[last] != check
    x += 1
  elsif current[first] != check && current[last] == check
    x += 1
  end
end
print x, "x"
