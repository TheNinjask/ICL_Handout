let f = fun n, b -> let x = new n s = new b in while !x>0 do s := !s + !x ; x := !x - 1 end; !s end end in println f(10,0)+f(100,20) end;; quit();;
