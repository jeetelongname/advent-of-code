#!/usr/bin/env ruby

lines = IO.readlines("input/day4-input")
## data cleaning
clean = []
i = 0
j = -1
loop do
  j += 1
  clean[j] = []
  loop do
    clean[j].append(lines[i]) unless lines[i] == "\n"
    i += 1
    if lines[i] == "\n"
      break
    elsif i > lines.length
      break
    end
  end
  break if i > lines.length
end

clean.each_with_index do |cl, i|
  clean[i] = cl.join(" ").gsub(/\n/, "").split
end

#part 1
count = 0
clean.each do |cl|
  if cl.length == 8
    count += 1
    next
  else
    x = 0

    cl.each do |c|
      if c.match(/cid:/)
        x += 1
      end
    end

    if x == 0 && cl.length == 7
      count += 1
    end
  end # if
end
# p count

#part 2
# More data cleaning
clean = clean.map do |cl|
  cl.map do |c|
    c.split(":")
  end
end

p clean[0]

def check(arr)
  x = 0
  case cl[0]
  when "byr"
    if cl[1].to_i >= 1920 && cl[1] <= 2002
      p ok
    end
  when "iyr"
    if cl[1].to_i >= 2010 && cl[1] <= 2020
      p ok
    end
  when "eyr"
    if cl[1].to_i >= 2020 && cl[1] <= 2030
      p ok
    end
  when "hgt"
  when "hcl"
  when "ecl"
  when "pid"
  when "cid"
  end
end

clean.each do |cl|
  if cl.length == 8
    check(cl)
    next
  else
    x = 0

    if x == 0 && cl.length == 7
      count += 1
    end
  end # if
end
