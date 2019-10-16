void main()
{
	int i = 8;
	int f = i;
	i += f;
	i = i - 2;
	method(i, -20);
}

void method(int a, int b)
{
	System.out.println(a + b);
}