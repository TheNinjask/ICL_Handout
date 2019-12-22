let
person1 : struct = { name = "joe"; age = 22 }
person2 : struct = { name = "mary"; age = 5 }
person3 : struct = person1 + { tag = -2 }
in
println person1.age + person2.age;
println person3.tag + person3.age;
end;;	