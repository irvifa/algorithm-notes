package com.github.irvifa.algorithmnotes.leetcode.challenges.may.one.majorityelement;

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
  public void ableToFindMajorityElement() throws Exception {
    assertThat(underTest.majorityElement(new int[]{2,2,1,1,1,2,2}), is(equalTo(2)));
  }
}
