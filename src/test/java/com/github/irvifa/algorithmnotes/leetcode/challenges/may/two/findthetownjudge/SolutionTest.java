package com.github.irvifa.algorithmnotes.leetcode.challenges.may.two.findthetownjudge;

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
  public void findJudgeTest() throws Exception {
    assertThat(underTest.findJudge(2, new int[][] {{1, 2}}), is(equalTo(2)));
  }
}
