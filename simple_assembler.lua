solution = {}

solution.simple_assembler = function(program)
    local registers, stack, pointer = {}, {}, 1
    -- Define instructions
    local instructions = {
        mov = function(x, y)
            if type(y) == "string" then
                return function() registers[x] = registers[y]; pointer = pointer + 1; end
            end
            if type(y) == "number" then
                return function() registers[x] = y; pointer = pointer + 1; end
            end
        end;
        inc = function(x)
            return function() registers[x] = registers[x] + 1; pointer = pointer + 1; end
        end;
        dec = function(x)
            return function() registers[x] = registers[x] - 1; pointer = pointer + 1; end
        end;
        jnz = function(x, y)
            if type(x) == "string" then
                return function() pointer = pointer + (registers[x] ~= 0 and y or 1); end
            end
            if type(x) == "number" then
                return function() pointer = pointer + (x ~= 0 and y or 1); end
            end
        end;
    }

    -- Parse lines into instructions
    local function parse(str)
        local function t(s) return s:match("-?%d+") and tonumber(s) or s; end
        if #str > 5 then
            return instructions[string.sub(str, 0, 3)](string.sub(str, 5, 5), t(string.sub(str, 7)))
        else
            return instructions[string.sub(str, 0, 3)](string.sub(str, 5, 5))
        end
    end

    -- Parse program into instructions
    for k, v in ipairs(program) do
        stack[k] = parse(v)
    end

    -- Execute program
    while pointer <= #stack do
        stack[pointer]()
    end

    return registers;
end

return solution
