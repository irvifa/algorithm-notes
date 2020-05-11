package com.github.irvifa.algorithmnotes.leetcode.challenges.may.two.checkifitisastraightline;

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
  public void findIfItIsAStraightLine() throws Exception {
    int[][] input = new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}};
    assertThat(underTest.checkStraightLine(input), is(equalTo(true)));
  }
}
