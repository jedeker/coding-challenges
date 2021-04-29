local solution = {}

-- Create a function that takes a Roman numeral as its argument and returns
-- its value as a numeric decimal integer.
local function chars(str)
  local function iter(str, i)
    i = i + 1
    if i <= str:len() then
      return i, str:sub(i, i)
    end
  end
  return iter, str, 0
end

solution.roman_Decoder = function(roman) 
  local symbols = {["I"] = 1, ["V"] = 5, ["X"] = 10, ["L"] = 50, ["C"] = 100, ["D"] = 500, ["M"] = 1000}
  local value, previous = 0, nil;
  for i, c in chars(roman:reverse()) do
    if previous then
      if symbols[c] < previous then
        value = value - symbols[c]  
      else
        value = value + symbols[c]
      end
    else
      value = value + symbols[c]
    end
    previous = symbols[c]
  end
  return value
end

return solution
