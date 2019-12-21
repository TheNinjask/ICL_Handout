println ((new 3) := 6);;
println 11111;;
let a: ref int = new 5 in a := !a + 1; println (!a) end;;
println 11111;;
let x : ref int = new 10
s : ref int = new 0 in
while !x>0 do
s := !s + !x ; x := !x - 1
end; println (!s)
end;;	
println 11111;;
let f : ( int, int) int = fun n: int, b : int ->
let
x : ref int = new n
s : ref int = new b
in
while !x>0 do
s := !s + !x ; x := !x - 1
end;
!s
end
end
in println (f(10,0)+f(100,20))
end;;
println 11111;;

