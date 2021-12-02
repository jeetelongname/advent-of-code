#!/usr/bin/env ruby

# cleaned
lines = IO.readlines("input/day3-input").map { |x| x.sub(/\n$/, "") }

## part 1
x = 0
tree = 0 # 420 my dude
lines.each do |line|
  char = line[x % line.length]
  tree += 1 if char == "#"

  x += 3
end
p tree

## part 2
slopes = [[1, 1], [3, 1], [5, 1], [7, 1], [1, 2]]
collisions = []
slopes.each do |slope|
  tree = 0
  x = 0
  y = 0
  while y != lines.length
    char = lines[y][x % lines[y].length]
    tree += 1 if char == "#"

    x += slope[0]
    y += slope[1]
    break if y == lines.length + 1
  end
  collisions.append(tree)
end

finalie = 1
collisions.each do |i|
  finalie = i * finalie
end

p finalie
