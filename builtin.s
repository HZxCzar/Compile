	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p0_m2p0_a2p0_c2p0"
	.file	"builtin.c"
	.globl	print
	.p2align	1
	.type	print,@function
print:
	lui	a1, %hi(.L.str)
	addi	a1, a1, %lo(.L.str)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end0:
	.size	print, .Lfunc_end0-print

	.globl	println
	.p2align	1
	.type	println,@function
println:
	lui	a1, %hi(.L.str.1)
	addi	a1, a1, %lo(.L.str.1)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end1:
	.size	println, .Lfunc_end1-println

	.globl	printInt
	.p2align	1
	.type	printInt,@function
printInt:
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end2:
	.size	printInt, .Lfunc_end2-printInt

	.globl	printlnInt
	.p2align	1
	.type	printlnInt,@function
printlnInt:
	lui	a1, %hi(.L.str.3)
	addi	a1, a1, %lo(.L.str.3)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end3:
	.size	printlnInt, .Lfunc_end3-printlnInt

	.globl	__builtin_array.size
	.p2align	1
	.type	__builtin_array.size,@function
__builtin_array.size:
	lw	a0, -4(a0)
	ret
.Lfunc_end4:
	.size	__builtin_array.size, .Lfunc_end4-__builtin_array.size

	.globl	_malloc
	.p2align	1
	.type	_malloc,@function
_malloc:
	tail	malloc
.Lfunc_end5:
	.size	_malloc, .Lfunc_end5-_malloc

	.globl	__malloc_array
	.p2align	1
	.type	__malloc_array,@function
__malloc_array:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	mv	s0, a0
	mul	a0, a1, a0
	addi	a0, a0, 4
	call	malloc
	addi	a1, a0, 4
	sw	s0, 0(a0)
	mv	a0, a1
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end6:
	.size	__malloc_array, .Lfunc_end6-__malloc_array

	.globl	getString
	.p2align	1
	.type	getString,@function
getString:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	li	a0, 102
	call	malloc
	mv	s0, a0
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	mv	a1, s0
	call	scanf
	mv	a0, s0
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end7:
	.size	getString, .Lfunc_end7-getString

	.globl	getInt
	.p2align	1
	.type	getInt,@function
getInt:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, sp, 8
	call	scanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end8:
	.size	getInt, .Lfunc_end8-getInt

	.globl	toString
	.p2align	1
	.type	toString,@function
toString:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	sw	s1, 4(sp)
	mv	s0, a0
	li	a0, 12
	call	malloc
	mv	s1, a0
	lui	a0, %hi(.L.str.2)
	addi	a1, a0, %lo(.L.str.2)
	mv	a0, s1
	mv	a2, s0
	call	sprintf
	mv	a0, s1
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	lw	s1, 4(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end9:
	.size	toString, .Lfunc_end9-toString

	.globl	Bool_string.toString
	.p2align	1
	.type	Bool_string.toString,@function
Bool_string.toString:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	mv	s0, a0
	li	a0, 12
	call	malloc
	ori	a1, s0, 48
	sb	zero, 1(a0)
	sb	a1, 0(a0)
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end10:
	.size	Bool_string.toString, .Lfunc_end10-Bool_string.toString

	.globl	__string.length
	.p2align	1
	.type	__string.length,@function
__string.length:
	li	a1, 0
.LBB11_1:
	add	a2, a0, a1
	lbu	a2, 0(a2)
	addi	a1, a1, 1
	bnez	a2, .LBB11_1
	addi	a0, a1, -1
	ret
.Lfunc_end11:
	.size	__string.length, .Lfunc_end11-__string.length

	.globl	__string.substring
	.p2align	1
	.type	__string.substring,@function
__string.substring:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	sw	s1, 4(sp)
	sw	s2, 0(sp)
	mv	s0, a1
	mv	s2, a0
	sub	s1, a2, a1
	addi	a0, s1, 1
	call	malloc
	blez	s1, .LBB12_3
	add	a1, s2, s0
	mv	a2, a0
	mv	a3, s1
.LBB12_2:
	lb	a4, 0(a1)
	sb	a4, 0(a2)
	addi	a3, a3, -1
	addi	a2, a2, 1
	addi	a1, a1, 1
	bnez	a3, .LBB12_2
.LBB12_3:
	add	a1, a0, s1
	sb	zero, 0(a1)
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	lw	s1, 4(sp)
	lw	s2, 0(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end12:
	.size	__string.substring, .Lfunc_end12-__string.substring

	.globl	__string.parseInt
	.p2align	1
	.type	__string.parseInt,@function
__string.parseInt:
	lbu	a1, 0(a0)
	addi	a2, a1, -45
	seqz	a2, a2
	add	a3, a0, a2
	lbu	a2, 0(a3)
	beqz	a2, .LBB13_6
	li	a0, 0
	addi	a3, a3, 1
	li	a4, 10
.LBB13_2:
	andi	a5, a2, 255
	lbu	a2, 0(a3)
	mul	a0, a0, a4
	add	a0, a0, a5
	addi	a0, a0, -48
	addi	a3, a3, 1
	bnez	a2, .LBB13_2
	li	a2, 45
	bne	a1, a2, .LBB13_5
.LBB13_4:
	neg	a0, a0
.LBB13_5:
	ret
.LBB13_6:
	li	a0, 0
	li	a2, 45
	beq	a1, a2, .LBB13_4
	j	.LBB13_5
.Lfunc_end13:
	.size	__string.parseInt, .Lfunc_end13-__string.parseInt

	.globl	__string.ord
	.p2align	1
	.type	__string.ord,@function
__string.ord:
	add	a0, a0, a1
	lbu	a0, 0(a0)
	ret
.Lfunc_end14:
	.size	__string.ord, .Lfunc_end14-__string.ord

	.globl	__string.compare
	.p2align	1
	.type	__string.compare,@function
__string.compare:
	lbu	a2, 0(a0)
	beqz	a2, .LBB15_5
	li	a3, 0
	addi	a0, a0, 1
.LBB15_2:
	add	a4, a1, a3
	lbu	a4, 0(a4)
	beqz	a4, .LBB15_6
	andi	a5, a2, 255
	bne	a5, a4, .LBB15_8
	add	a2, a0, a3
	lbu	a2, 0(a2)
	addi	a4, a3, 1
	mv	a3, a4
	bnez	a2, .LBB15_2
	j	.LBB15_7
.LBB15_5:
	li	a4, 0
	j	.LBB15_7
.LBB15_6:
	mv	a4, a3
.LBB15_7:
	add	a0, a1, a4
	lbu	a4, 0(a0)
.LBB15_8:
	andi	a0, a2, 255
	sub	a0, a0, a4
	ret
.Lfunc_end15:
	.size	__string.compare, .Lfunc_end15-__string.compare

	.globl	__string.concat
	.p2align	1
	.type	__string.concat,@function
__string.concat:
	addi	sp, sp, -32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	sw	s1, 20(sp)
	sw	s2, 16(sp)
	sw	s3, 12(sp)
	sw	s4, 8(sp)
	sw	s5, 4(sp)
	mv	s2, a1
	mv	s3, a0
	li	s4, 0
	li	s5, -1
.LBB16_1:
	mv	s1, s4
	add	a0, s3, s4
	lbu	a0, 0(a0)
	addi	s4, s4, 1
	addi	s5, s5, 1
	bnez	a0, .LBB16_1
	li	s0, -1
.LBB16_3:
	add	a0, s2, s0
	lbu	a0, 1(a0)
	addi	s0, s0, 1
	bnez	a0, .LBB16_3
	add	a0, s4, s0
	call	malloc
	beqz	s1, .LBB16_7
	li	a1, 0
.LBB16_6:
	add	a2, s3, a1
	lb	a2, 0(a2)
	add	a3, a0, a1
	addi	a1, a1, 1
	sb	a2, 0(a3)
	bne	s1, a1, .LBB16_6
.LBB16_7:
	beqz	s0, .LBB16_10
	li	a1, 0
	add	a2, a0, s5
.LBB16_9:
	add	a3, s2, a1
	lb	a3, 0(a3)
	add	a4, a2, a1
	addi	a1, a1, 1
	sb	a3, 0(a4)
	bne	s0, a1, .LBB16_9
.LBB16_10:
	add	a1, a0, s4
	add	a1, a1, s0
	sb	zero, -1(a1)
	lw	ra, 28(sp)
	lw	s0, 24(sp)
	lw	s1, 20(sp)
	lw	s2, 16(sp)
	lw	s3, 12(sp)
	lw	s4, 8(sp)
	lw	s5, 4(sp)
	addi	sp, sp, 32
	ret
.Lfunc_end16:
	.size	__string.concat, .Lfunc_end16-__string.concat

	.globl	__string.copy
	.p2align	1
	.type	__string.copy,@function
__string.copy:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	sw	s1, 4(sp)
	sw	s2, 0(sp)
	mv	s1, a1
	mv	s2, a0
	li	s0, -1
.LBB17_1:
	add	a0, s1, s0
	lbu	a0, 1(a0)
	addi	s0, s0, 1
	bnez	a0, .LBB17_1
	addi	a0, s0, 1
	call	malloc
	sw	a0, 0(s2)
	beqz	s0, .LBB17_6
	li	a0, 0
.LBB17_4:
	lw	a1, 0(s2)
	add	a2, s1, a0
	lb	a2, 0(a2)
	add	a1, a1, a0
	addi	a0, a0, 1
	sb	a2, 0(a1)
	bne	s0, a0, .LBB17_4
	lw	a0, 0(s2)
.LBB17_6:
	add	a0, a0, s0
	sb	zero, 0(a0)
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	lw	s1, 4(sp)
	lw	s2, 0(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end17:
	.size	__string.copy, .Lfunc_end17-__string.copy

	.type	.L.str,@object
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3

	.type	.L.str.1,@object
.L.str.1:
	.asciz	"%s\n"
	.size	.L.str.1, 4

	.type	.L.str.2,@object
.L.str.2:
	.asciz	"%d"
	.size	.L.str.2, 3

	.type	.L.str.3,@object
.L.str.3:
	.asciz	"%d\n"
	.size	.L.str.3, 4

	.ident	"Ubuntu clang version 15.0.7"
	.section	".note.GNU-stack","",@progbits
	.addrsig
