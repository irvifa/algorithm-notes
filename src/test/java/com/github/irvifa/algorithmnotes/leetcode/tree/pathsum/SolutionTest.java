package com.github.irvifa.algorithmnotes.leetcode.tree.pathsum;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import com.github.irvifa.algorithmnotes.leetcode.tree.Helper;
import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;
import org.junit.Before;
import org.junit.Test;

public class SolutionTest {
  private Solution underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new Solution();
  }

  @Test
  public void getPathSum() throws Exception {
    String input = "5,4,8,11,null,13,4,7,2,null,null,null,1";
    int target = 22;
    TreeNode root = Helper.stringToTreeNode(input);
    boolean result = underTest.hasPathSum(root, target);
    assertThat(result, is(equalTo(true)));
  }
}
