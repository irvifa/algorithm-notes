package com.github.irvifa.algorithmnotes.leetcode.arrays.dietplanperformance;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SolutionTest {
  private Solution underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new Solution();
  }

  @Test
  public void dietPlanPerformanceSuccess() throws Exception {
    int[] calories = new int[]{1,2,3,4,5};
    int k = 1;
    int lower = 3;
    int upper = 3;
    assertThat(underTest.dietPlanPerformance(calories, k, lower, upper), is(equalTo(0)));
  }
}
