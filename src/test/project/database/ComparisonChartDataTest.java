package project.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComparisonChartDataTest
{
	ComparisonChartData chartData;
	@BeforeEach
	void setUp() throws NoSuchAlgorithmException
	{
		chartData = new ComparisonChartData();
	}

	@Test
	void testGenerateGraphTwoNodes()
	{
		chartData.generateGraph(2);
		assertEquals(2, chartData.getNodes().size());
	}

	@Test
	void testGenerateGraphNegativeNumber()
	{
		assertThrows(IllegalArgumentException.class, () -> chartData.generateGraph(-1),
				"generateGraph: Number of nodes must be greater than 1");
	}

	@Test
	void testGenerateGraphZero()
	{
		assertThrows(IllegalArgumentException.class, () -> chartData.generateGraph(0),
				"generateGraph: Number of nodes must be greater than 1");
	}

	@Test
	void testGetComparisonsTwoNodes()
	{
		chartData.generateGraph(2);
		assertEquals(7, chartData.getComparisons(2));
	}

	@Test
	void testGetComparisonsNegativeNumber()
	{
		assertThrows(IllegalArgumentException.class, () -> chartData.getComparisons(-1),
				"getComparisons: Number of nodes must be greater than 1");
	}

	@Test
	void testGetComparisonsZero()
	{
		assertThrows(IllegalArgumentException.class, () -> chartData.getComparisons(0),
				"getComparisons: Number of nodes must be greater than 1");
	}
}
