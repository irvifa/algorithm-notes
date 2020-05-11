package com.github.irvifa.algorithmnotes.leetcode.challenges.may.one.numbercomplement;

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
  public void ableToFindNumberComplement() throws Exception {
    assertThat(underTest.findComplement(5), is(equalTo(2)));
  }
}
