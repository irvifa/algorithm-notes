package com.github.irvifa.algorithmnotes.leetcode.twopointers.threesum;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SolutionTest {
  private Solution underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new Solution();
  }

  @Test
  public void getThreeSum() throws Exception {
    List<List<Integer>> result = underTest.threeSum(new int[] {-1,0,1,2,-1,-4});
    List<List<Integer>> expected = new ArrayList<>();
    expected.add(Arrays.asList(-1,-1,2));
    expected.add(Arrays.asList(-1,0,1));
    assertThat(result, is(equalTo(expected)));
  }
}
