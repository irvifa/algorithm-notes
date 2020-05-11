package com.github.irvifa.algorithmnotes.leetcode.challenges.may.two.floodfill;

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
  public void performFloodFill() throws Exception {
    int[][] input = new int[][]{{1,1,1}, {1,1,0}, {1,0,1}};
    int[][] expected = new int[][]{{2,2,2}, {2,2,0}, {2,0,1}};
    assertThat(underTest.floodFill(input, 1, 1, 2), is(equalTo(expected)));
  }
}
