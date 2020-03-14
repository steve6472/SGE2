uniform int seed;
uniform int octaves;
uniform int amplitude;
uniform int smoothness;
uniform int heightOffset;

uniform float roughness;

float getOne()
{
	return 1;
}

float getNoise(int n)
{
	n += seed;
	n = (n << 13) ^ n;
	float newN = (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;

	return 1.0 - (newN / 1073741824.0);
}

float getNoise(float x, float z)
{
	return getNoise(int(x + z * 57));
}

float lerp(float a, float b, float z)
{
	float mu2 = (1 - cos(z * 3.14)) / 2;
	return (a * (1 - mu2) + b * mu2);
}

float noise(float x, float z)
{
	float floorX = float(int(x));
	float floorZ = float(int(z));

	float s = getNoise(floorX, 	floorZ);
	float t = getNoise(floorX + 1, floorZ);
	float u = getNoise(floorX, 	floorZ + 1);
	float v = getNoise(floorX + 1, floorZ + 1);
	
	float rec1 = lerp(s, t, x - floorX);
	float rec2 = lerp(u, v, x - floorX);
	float rec3 = lerp(rec1 ,rec2, z - floorZ);
	
	return rec3;
}

float getHeight(int x, int z, int chunkX, int chunkZ, int CHUNK_SIZE)
{
	float newX = (x + chunkX * CHUNK_SIZE);
	float newZ = (z + chunkZ * CHUNK_SIZE);
	
	float totalValue = 0;
	
	for (int i = 0; i < octaves - 1; i++)
	{
		float frequency = pow(2.0, i);
		float amplitude = pow(roughness, i);
		totalValue += noise(newX * frequency / smoothness, newZ * frequency / smoothness) * amplitude;
	}

	float val = (((totalValue / 2.1) + 1.2) * amplitude) + heightOffset;

	return val > 0 ? val : 1;
}

float getHeight(int x, int z)
{
	return getHeight(x, z, 0, 0, 0);
}