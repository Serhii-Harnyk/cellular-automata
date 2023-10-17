package sharnyk.oned;

import org.junit.Assert;
import org.junit.Test;
import sharnyk.testca.mapgen.oned.rules.Rule1d;
import sharnyk.testca.mapgen.oned.rules.Rule30;
import sharnyk.testca.mapgen.oned.rules.ShiftRightRule;

public class RulesTest {

    @Test
    public void ShiftRightRuleTest() {
        Rule1d rule1d = new ShiftRightRule();

        int[][] field = new int[3][3];

        field[0][0] = 1;

        Assert.assertEquals(1, rule1d.apply(field[0], 1, 1));
        Assert.assertEquals(0, rule1d.apply(field[0], 0, 1));
    }

    @Test
    public void IdentityRuleTest() {
        Rule1d rule1d = (field, position, neighSize) -> field[position];

        int[][] field = new int[3][3];
        field[0][0] = 1;

        Assert.assertEquals(1, rule1d.apply(field[0], 0, 1));
        Assert.assertEquals(0, rule1d.apply(field[0], 1, 1));

    }

    @Test
    public void Rule30Test() {
        Rule1d rule1d = new Rule30();
        int[][] field = new int[5][5];
        field[0][2] = 1;

        Assert.assertEquals(1, rule1d.apply(field[0], 1, 1));
        Assert.assertEquals(1, rule1d.apply(field[0], 2, 1));
        Assert.assertEquals(1, rule1d.apply(field[0], 3, 1));

    }
}
