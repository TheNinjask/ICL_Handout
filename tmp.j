.class public Main
.super java/lang/Object
.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method
.method public static main([Ljava/lang/String;)V
	.limit locals 10
	.limit stack 256
	getstatic java/lang/System/out Ljava/io/PrintStream;
	aconst_null
	astore 4
	new f0
	dup
		invokespecial f0/<init>()V
	dup
	aload 4
	putfield f0/sl Ljava/lang/Object;
	astore 4
	aload 4
	sipush 1
	putfield f0/x0 I
	new f1
	dup
		invokespecial f1/<init>()V
	dup
	aload 4
	putfield f1/sl Lf0;
	astore 4
	aload 4
	sipush 2
	aload 4
	getfield f1/sl Lf0;
	getfield f0/x0 I
	iadd
	putfield f1/x0 I
	aload 4
	getfield f1/sl Lf0;
	getfield f0/x0 I
	aload 4
	getfield f1/x0 I
	iadd
	aload 4
	getfield f1/sl Lf0;
	astore 4
	aload 4
	getfield f0/x0 I
	iadd
	aload 4
	getfield f0/sl Ljava/lang/Object;
	astore 4
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	return
.end method