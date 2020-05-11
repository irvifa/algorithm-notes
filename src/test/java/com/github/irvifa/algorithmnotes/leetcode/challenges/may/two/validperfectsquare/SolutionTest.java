package com.github.irvifa.algorithmnotes.leetcode.challenges.may.two.validperfectsquare;

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
  public void isNotAPerfectSquare() throws Exception {
    assertThat(underTest.isPerfectSquare(14), is(equalTo(false)));
  }

  @Test
  public void isAPerfectSquare() throws Exception {
    assertThat(underTest.isPerfectSquare(16), is(equalTo(true)));
  }

}
