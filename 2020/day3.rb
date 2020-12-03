#!/usr/bin/env ruby

# cleaned
lines = IO.readlines("input/day3-input")
# p lines[-1]
# lines = IO.readlines("input/day3-example")
lines.each_with_index do |line, i|
  lines[i] = line.sub(/\n$/, "")
end

x = 0
tree = 0 # 420 my dude
lines.each do |line|
  if x > line.length
    # CARRYOVER
    x = (x % line.length)
  end

  char = line[x]
  if char == "#"
    tree += 1
  end
  x += 3
end
# p x
p tree
