/* Created by Pgen */
package pers.xia.jpython.grammar;

import java.util.Map;
import java.util.HashMap;

import pers.xia.jpython.tokenizer.TokState;

public final class GramInit{

    public static Arc[] arcs_0_0 = {
        new Arc(GramInit.labels[2], GramInit.states_0[0]),
        new Arc(GramInit.labels[7], GramInit.states_0[1]),
        new Arc(GramInit.labels[6], GramInit.states_0[0]),
    };

    public static Arc[] arcs_0_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_0 = {
        new State(3, GramInit.arcs_0_0),
        new State(1, GramInit.arcs_0_1),
    };

    public static Arc[] arcs_1_0 = {
        new Arc(GramInit.labels[3], GramInit.states_1[2]),
        new Arc(GramInit.labels[2], GramInit.states_1[2]),
        new Arc(GramInit.labels[4], GramInit.states_1[1]),
    };

    public static Arc[] arcs_1_1 = {
        new Arc(GramInit.labels[2], GramInit.states_1[2]),
    };

    public static Arc[] arcs_1_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_1 = {
        new State(3, GramInit.arcs_1_0),
        new State(1, GramInit.arcs_1_1),
        new State(1, GramInit.arcs_1_2),
    };

    public static Arc[] arcs_2_0 = {
        new Arc(GramInit.labels[9], GramInit.states_2[2]),
    };

    public static Arc[] arcs_2_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_2_2 = {
        new Arc(GramInit.labels[2], GramInit.states_2[2]),
        new Arc(GramInit.labels[7], GramInit.states_2[1]),
    };

    public static State[] states_2 = {
        new State(1, GramInit.arcs_2_0),
        new State(1, GramInit.arcs_2_1),
        new State(2, GramInit.arcs_2_2),
    };

    public static Arc[] arcs_3_0 = {
        new Arc(GramInit.labels[11], GramInit.states_3[3]),
    };

    public static Arc[] arcs_3_1 = {
        new Arc(GramInit.labels[2], GramInit.states_3[4]),
        new Arc(GramInit.labels[13], GramInit.states_3[2]),
    };

    public static Arc[] arcs_3_2 = {
        new Arc(GramInit.labels[15], GramInit.states_3[5]),
        new Arc(GramInit.labels[14], GramInit.states_3[2]),
    };

    public static Arc[] arcs_3_3 = {
        new Arc(GramInit.labels[12], GramInit.states_3[1]),
    };

    public static Arc[] arcs_3_4 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_3_5 = {
        new Arc(GramInit.labels[2], GramInit.states_3[4]),
    };

    public static State[] states_3 = {
        new State(1, GramInit.arcs_3_0),
        new State(2, GramInit.arcs_3_1),
        new State(2, GramInit.arcs_3_2),
        new State(1, GramInit.arcs_3_3),
        new State(1, GramInit.arcs_3_4),
        new State(1, GramInit.arcs_3_5),
    };

    public static Arc[] arcs_4_0 = {
        new Arc(GramInit.labels[10], GramInit.states_4[1]),
    };

    public static Arc[] arcs_4_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[10], GramInit.states_4[1]),
    };

    public static State[] states_4 = {
        new State(1, GramInit.arcs_4_0),
        new State(2, GramInit.arcs_4_1),
    };

    public static Arc[] arcs_5_0 = {
        new Arc(GramInit.labels[16], GramInit.states_5[2]),
    };

    public static Arc[] arcs_5_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_5_2 = {
        new Arc(GramInit.labels[19], GramInit.states_5[1]),
        new Arc(GramInit.labels[18], GramInit.states_5[1]),
        new Arc(GramInit.labels[20], GramInit.states_5[1]),
    };

    public static State[] states_5 = {
        new State(1, GramInit.arcs_5_0),
        new State(1, GramInit.arcs_5_1),
        new State(3, GramInit.arcs_5_2),
    };

    public static Arc[] arcs_6_0 = {
        new Arc(GramInit.labels[21], GramInit.states_6[1]),
    };

    public static Arc[] arcs_6_1 = {
        new Arc(GramInit.labels[19], GramInit.states_6[2]),
    };

    public static Arc[] arcs_6_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_6 = {
        new State(1, GramInit.arcs_6_0),
        new State(1, GramInit.arcs_6_1),
        new State(1, GramInit.arcs_6_2),
    };

    public static Arc[] arcs_7_0 = {
        new Arc(GramInit.labels[22], GramInit.states_7[1]),
    };

    public static Arc[] arcs_7_1 = {
        new Arc(GramInit.labels[23], GramInit.states_7[3]),
    };

    public static Arc[] arcs_7_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_7_3 = {
        new Arc(GramInit.labels[24], GramInit.states_7[4]),
    };

    public static Arc[] arcs_7_4 = {
        new Arc(GramInit.labels[27], GramInit.states_7[6]),
        new Arc(GramInit.labels[25], GramInit.states_7[5]),
    };

    public static Arc[] arcs_7_5 = {
        new Arc(GramInit.labels[26], GramInit.states_7[7]),
    };

    public static Arc[] arcs_7_6 = {
        new Arc(GramInit.labels[28], GramInit.states_7[2]),
    };

    public static Arc[] arcs_7_7 = {
        new Arc(GramInit.labels[27], GramInit.states_7[6]),
    };

    public static State[] states_7 = {
        new State(1, GramInit.arcs_7_0),
        new State(1, GramInit.arcs_7_1),
        new State(1, GramInit.arcs_7_2),
        new State(1, GramInit.arcs_7_3),
        new State(2, GramInit.arcs_7_4),
        new State(1, GramInit.arcs_7_5),
        new State(1, GramInit.arcs_7_6),
        new State(1, GramInit.arcs_7_7),
    };

    public static Arc[] arcs_8_0 = {
        new Arc(GramInit.labels[13], GramInit.states_8[2]),
    };

    public static Arc[] arcs_8_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_8_2 = {
        new Arc(GramInit.labels[15], GramInit.states_8[1]),
        new Arc(GramInit.labels[29], GramInit.states_8[3]),
    };

    public static Arc[] arcs_8_3 = {
        new Arc(GramInit.labels[15], GramInit.states_8[1]),
    };

    public static State[] states_8 = {
        new State(1, GramInit.arcs_8_0),
        new State(1, GramInit.arcs_8_1),
        new State(2, GramInit.arcs_8_2),
        new State(1, GramInit.arcs_8_3),
    };

    public static Arc[] arcs_9_0 = {
        new Arc(GramInit.labels[30], GramInit.states_9[11]),
        new Arc(GramInit.labels[34], GramInit.states_9[5]),
        new Arc(GramInit.labels[33], GramInit.states_9[4]),
    };

    public static Arc[] arcs_9_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_9_2 = {
        new Arc(GramInit.labels[26], GramInit.states_9[3]),
    };

    public static Arc[] arcs_9_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_9[6]),
    };

    public static Arc[] arcs_9_4 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[30], GramInit.states_9[3]),
        new Arc(GramInit.labels[32], GramInit.states_9[6]),
    };

    public static Arc[] arcs_9_5 = {
        new Arc(GramInit.labels[30], GramInit.states_9[1]),
    };

    public static Arc[] arcs_9_6 = {
        new Arc(GramInit.labels[30], GramInit.states_9[9]),
        new Arc(GramInit.labels[34], GramInit.states_9[5]),
    };

    public static Arc[] arcs_9_7 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[30], GramInit.states_9[11]),
        new Arc(GramInit.labels[34], GramInit.states_9[5]),
        new Arc(GramInit.labels[33], GramInit.states_9[4]),
    };

    public static Arc[] arcs_9_8 = {
        new Arc(GramInit.labels[26], GramInit.states_9[10]),
    };

    public static Arc[] arcs_9_9 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[31], GramInit.states_9[2]),
        new Arc(GramInit.labels[32], GramInit.states_9[6]),
    };

    public static Arc[] arcs_9_10 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_9[7]),
    };

    public static Arc[] arcs_9_11 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[31], GramInit.states_9[8]),
        new Arc(GramInit.labels[32], GramInit.states_9[7]),
    };

    public static State[] states_9 = {
        new State(3, GramInit.arcs_9_0),
        new State(1, GramInit.arcs_9_1),
        new State(1, GramInit.arcs_9_2),
        new State(2, GramInit.arcs_9_3),
        new State(3, GramInit.arcs_9_4),
        new State(1, GramInit.arcs_9_5),
        new State(2, GramInit.arcs_9_6),
        new State(4, GramInit.arcs_9_7),
        new State(1, GramInit.arcs_9_8),
        new State(3, GramInit.arcs_9_9),
        new State(2, GramInit.arcs_9_10),
        new State(3, GramInit.arcs_9_11),
    };

    public static Arc[] arcs_10_0 = {
        new Arc(GramInit.labels[23], GramInit.states_10[1]),
    };

    public static Arc[] arcs_10_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[27], GramInit.states_10[3]),
    };

    public static Arc[] arcs_10_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_10_3 = {
        new Arc(GramInit.labels[26], GramInit.states_10[2]),
    };

    public static State[] states_10 = {
        new State(1, GramInit.arcs_10_0),
        new State(2, GramInit.arcs_10_1),
        new State(1, GramInit.arcs_10_2),
        new State(1, GramInit.arcs_10_3),
    };

    public static Arc[] arcs_11_0 = {
        new Arc(GramInit.labels[36], GramInit.states_11[3]),
        new Arc(GramInit.labels[34], GramInit.states_11[9]),
        new Arc(GramInit.labels[33], GramInit.states_11[4]),
    };

    public static Arc[] arcs_11_1 = {
        new Arc(GramInit.labels[26], GramInit.states_11[11]),
    };

    public static Arc[] arcs_11_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_11[6]),
    };

    public static Arc[] arcs_11_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[31], GramInit.states_11[1]),
        new Arc(GramInit.labels[32], GramInit.states_11[8]),
    };

    public static Arc[] arcs_11_4 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[36], GramInit.states_11[2]),
        new Arc(GramInit.labels[32], GramInit.states_11[6]),
    };

    public static Arc[] arcs_11_5 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_11_6 = {
        new Arc(GramInit.labels[36], GramInit.states_11[7]),
        new Arc(GramInit.labels[34], GramInit.states_11[9]),
    };

    public static Arc[] arcs_11_7 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[31], GramInit.states_11[10]),
        new Arc(GramInit.labels[32], GramInit.states_11[6]),
    };

    public static Arc[] arcs_11_8 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[36], GramInit.states_11[3]),
        new Arc(GramInit.labels[34], GramInit.states_11[9]),
        new Arc(GramInit.labels[33], GramInit.states_11[4]),
    };

    public static Arc[] arcs_11_9 = {
        new Arc(GramInit.labels[36], GramInit.states_11[5]),
    };

    public static Arc[] arcs_11_10 = {
        new Arc(GramInit.labels[26], GramInit.states_11[2]),
    };

    public static Arc[] arcs_11_11 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_11[8]),
    };

    public static State[] states_11 = {
        new State(3, GramInit.arcs_11_0),
        new State(1, GramInit.arcs_11_1),
        new State(2, GramInit.arcs_11_2),
        new State(3, GramInit.arcs_11_3),
        new State(3, GramInit.arcs_11_4),
        new State(1, GramInit.arcs_11_5),
        new State(2, GramInit.arcs_11_6),
        new State(3, GramInit.arcs_11_7),
        new State(4, GramInit.arcs_11_8),
        new State(1, GramInit.arcs_11_9),
        new State(1, GramInit.arcs_11_10),
        new State(2, GramInit.arcs_11_11),
    };

    public static Arc[] arcs_12_0 = {
        new Arc(GramInit.labels[23], GramInit.states_12[1]),
    };

    public static Arc[] arcs_12_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_12 = {
        new State(1, GramInit.arcs_12_0),
        new State(1, GramInit.arcs_12_1),
    };

    public static Arc[] arcs_13_0 = {
        new Arc(GramInit.labels[3], GramInit.states_13[1]),
        new Arc(GramInit.labels[4], GramInit.states_13[1]),
    };

    public static Arc[] arcs_13_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_13 = {
        new State(2, GramInit.arcs_13_0),
        new State(1, GramInit.arcs_13_1),
    };

    public static Arc[] arcs_14_0 = {
        new Arc(GramInit.labels[37], GramInit.states_14[1]),
    };

    public static Arc[] arcs_14_1 = {
        new Arc(GramInit.labels[2], GramInit.states_14[3]),
        new Arc(GramInit.labels[38], GramInit.states_14[2]),
    };

    public static Arc[] arcs_14_2 = {
        new Arc(GramInit.labels[37], GramInit.states_14[1]),
        new Arc(GramInit.labels[2], GramInit.states_14[3]),
    };

    public static Arc[] arcs_14_3 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_14 = {
        new State(1, GramInit.arcs_14_0),
        new State(2, GramInit.arcs_14_1),
        new State(2, GramInit.arcs_14_2),
        new State(1, GramInit.arcs_14_3),
    };

    public static Arc[] arcs_15_0 = {
        new Arc(GramInit.labels[42], GramInit.states_15[1]),
        new Arc(GramInit.labels[44], GramInit.states_15[1]),
        new Arc(GramInit.labels[39], GramInit.states_15[1]),
        new Arc(GramInit.labels[41], GramInit.states_15[1]),
        new Arc(GramInit.labels[40], GramInit.states_15[1]),
        new Arc(GramInit.labels[45], GramInit.states_15[1]),
        new Arc(GramInit.labels[46], GramInit.states_15[1]),
        new Arc(GramInit.labels[43], GramInit.states_15[1]),
    };

    public static Arc[] arcs_15_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_15 = {
        new State(8, GramInit.arcs_15_0),
        new State(1, GramInit.arcs_15_1),
    };

    public static Arc[] arcs_16_0 = {
        new Arc(GramInit.labels[47], GramInit.states_16[3]),
    };

    public static Arc[] arcs_16_1 = {
        new Arc(GramInit.labels[47], GramInit.states_16[4]),
        new Arc(GramInit.labels[49], GramInit.states_16[4]),
    };

    public static Arc[] arcs_16_2 = {
        new Arc(GramInit.labels[9], GramInit.states_16[5]),
        new Arc(GramInit.labels[49], GramInit.states_16[5]),
    };

    public static Arc[] arcs_16_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[31], GramInit.states_16[1]),
        new Arc(GramInit.labels[48], GramInit.states_16[2]),
    };

    public static Arc[] arcs_16_4 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[31], GramInit.states_16[1]),
    };

    public static Arc[] arcs_16_5 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_16 = {
        new State(1, GramInit.arcs_16_0),
        new State(2, GramInit.arcs_16_1),
        new State(2, GramInit.arcs_16_2),
        new State(3, GramInit.arcs_16_3),
        new State(2, GramInit.arcs_16_4),
        new State(1, GramInit.arcs_16_5),
    };

    public static Arc[] arcs_17_0 = {
        new Arc(GramInit.labels[50], GramInit.states_17[2]),
        new Arc(GramInit.labels[26], GramInit.states_17[2]),
    };

    public static Arc[] arcs_17_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[50], GramInit.states_17[2]),
        new Arc(GramInit.labels[26], GramInit.states_17[2]),
    };

    public static Arc[] arcs_17_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_17[1]),
    };

    public static State[] states_17 = {
        new State(2, GramInit.arcs_17_0),
        new State(3, GramInit.arcs_17_1),
        new State(2, GramInit.arcs_17_2),
    };

    public static Arc[] arcs_18_0 = {
        new Arc(GramInit.labels[61], GramInit.states_18[1]),
        new Arc(GramInit.labels[59], GramInit.states_18[1]),
        new Arc(GramInit.labels[62], GramInit.states_18[1]),
        new Arc(GramInit.labels[60], GramInit.states_18[1]),
        new Arc(GramInit.labels[56], GramInit.states_18[1]),
        new Arc(GramInit.labels[57], GramInit.states_18[1]),
        new Arc(GramInit.labels[53], GramInit.states_18[1]),
        new Arc(GramInit.labels[55], GramInit.states_18[1]),
        new Arc(GramInit.labels[63], GramInit.states_18[1]),
        new Arc(GramInit.labels[54], GramInit.states_18[1]),
        new Arc(GramInit.labels[52], GramInit.states_18[1]),
        new Arc(GramInit.labels[58], GramInit.states_18[1]),
        new Arc(GramInit.labels[51], GramInit.states_18[1]),
    };

    public static Arc[] arcs_18_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_18 = {
        new State(13, GramInit.arcs_18_0),
        new State(1, GramInit.arcs_18_1),
    };

    public static Arc[] arcs_19_0 = {
        new Arc(GramInit.labels[64], GramInit.states_19[2]),
    };

    public static Arc[] arcs_19_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_19_2 = {
        new Arc(GramInit.labels[65], GramInit.states_19[1]),
    };

    public static State[] states_19 = {
        new State(1, GramInit.arcs_19_0),
        new State(1, GramInit.arcs_19_1),
        new State(1, GramInit.arcs_19_2),
    };

    public static Arc[] arcs_20_0 = {
        new Arc(GramInit.labels[66], GramInit.states_20[1]),
    };

    public static Arc[] arcs_20_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_20 = {
        new State(1, GramInit.arcs_20_0),
        new State(1, GramInit.arcs_20_1),
    };

    public static Arc[] arcs_21_0 = {
        new Arc(GramInit.labels[69], GramInit.states_21[1]),
        new Arc(GramInit.labels[67], GramInit.states_21[1]),
        new Arc(GramInit.labels[68], GramInit.states_21[1]),
        new Arc(GramInit.labels[71], GramInit.states_21[1]),
        new Arc(GramInit.labels[70], GramInit.states_21[1]),
    };

    public static Arc[] arcs_21_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_21 = {
        new State(5, GramInit.arcs_21_0),
        new State(1, GramInit.arcs_21_1),
    };

    public static Arc[] arcs_22_0 = {
        new Arc(GramInit.labels[72], GramInit.states_22[1]),
    };

    public static Arc[] arcs_22_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_22 = {
        new State(1, GramInit.arcs_22_0),
        new State(1, GramInit.arcs_22_1),
    };

    public static Arc[] arcs_23_0 = {
        new Arc(GramInit.labels[73], GramInit.states_23[1]),
    };

    public static Arc[] arcs_23_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_23 = {
        new State(1, GramInit.arcs_23_0),
        new State(1, GramInit.arcs_23_1),
    };

    public static Arc[] arcs_24_0 = {
        new Arc(GramInit.labels[74], GramInit.states_24[1]),
    };

    public static Arc[] arcs_24_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[9], GramInit.states_24[2]),
    };

    public static Arc[] arcs_24_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_24 = {
        new State(1, GramInit.arcs_24_0),
        new State(2, GramInit.arcs_24_1),
        new State(1, GramInit.arcs_24_2),
    };

    public static Arc[] arcs_25_0 = {
        new Arc(GramInit.labels[49], GramInit.states_25[1]),
    };

    public static Arc[] arcs_25_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_25 = {
        new State(1, GramInit.arcs_25_0),
        new State(1, GramInit.arcs_25_1),
    };

    public static Arc[] arcs_26_0 = {
        new Arc(GramInit.labels[75], GramInit.states_26[2]),
    };

    public static Arc[] arcs_26_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[76], GramInit.states_26[4]),
    };

    public static Arc[] arcs_26_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[26], GramInit.states_26[1]),
    };

    public static Arc[] arcs_26_3 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_26_4 = {
        new Arc(GramInit.labels[26], GramInit.states_26[3]),
    };

    public static State[] states_26 = {
        new State(1, GramInit.arcs_26_0),
        new State(2, GramInit.arcs_26_1),
        new State(2, GramInit.arcs_26_2),
        new State(1, GramInit.arcs_26_3),
        new State(1, GramInit.arcs_26_4),
    };

    public static Arc[] arcs_27_0 = {
        new Arc(GramInit.labels[78], GramInit.states_27[1]),
        new Arc(GramInit.labels[77], GramInit.states_27[1]),
    };

    public static Arc[] arcs_27_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_27 = {
        new State(2, GramInit.arcs_27_0),
        new State(1, GramInit.arcs_27_1),
    };

    public static Arc[] arcs_28_0 = {
        new Arc(GramInit.labels[79], GramInit.states_28[2]),
    };

    public static Arc[] arcs_28_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_28_2 = {
        new Arc(GramInit.labels[80], GramInit.states_28[1]),
    };

    public static State[] states_28 = {
        new State(1, GramInit.arcs_28_0),
        new State(1, GramInit.arcs_28_1),
        new State(1, GramInit.arcs_28_2),
    };

    public static Arc[] arcs_29_0 = {
        new Arc(GramInit.labels[76], GramInit.states_29[5]),
    };

    public static Arc[] arcs_29_1 = {
        new Arc(GramInit.labels[83], GramInit.states_29[7]),
        new Arc(GramInit.labels[33], GramInit.states_29[7]),
        new Arc(GramInit.labels[13], GramInit.states_29[4]),
    };

    public static Arc[] arcs_29_2 = {
        new Arc(GramInit.labels[79], GramInit.states_29[1]),
        new Arc(GramInit.labels[81], GramInit.states_29[2]),
        new Arc(GramInit.labels[82], GramInit.states_29[2]),
        new Arc(GramInit.labels[12], GramInit.states_29[6]),
    };

    public static Arc[] arcs_29_3 = {
        new Arc(GramInit.labels[15], GramInit.states_29[7]),
    };

    public static Arc[] arcs_29_4 = {
        new Arc(GramInit.labels[83], GramInit.states_29[3]),
    };

    public static Arc[] arcs_29_5 = {
        new Arc(GramInit.labels[81], GramInit.states_29[2]),
        new Arc(GramInit.labels[82], GramInit.states_29[2]),
        new Arc(GramInit.labels[12], GramInit.states_29[6]),
    };

    public static Arc[] arcs_29_6 = {
        new Arc(GramInit.labels[79], GramInit.states_29[1]),
    };

    public static Arc[] arcs_29_7 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_29 = {
        new State(1, GramInit.arcs_29_0),
        new State(3, GramInit.arcs_29_1),
        new State(4, GramInit.arcs_29_2),
        new State(1, GramInit.arcs_29_3),
        new State(1, GramInit.arcs_29_4),
        new State(3, GramInit.arcs_29_5),
        new State(1, GramInit.arcs_29_6),
        new State(1, GramInit.arcs_29_7),
    };

    public static Arc[] arcs_30_0 = {
        new Arc(GramInit.labels[23], GramInit.states_30[1]),
    };

    public static Arc[] arcs_30_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[85], GramInit.states_30[0]),
    };

    public static State[] states_30 = {
        new State(1, GramInit.arcs_30_0),
        new State(2, GramInit.arcs_30_1),
    };

    public static Arc[] arcs_31_0 = {
        new Arc(GramInit.labels[12], GramInit.states_31[3]),
    };

    public static Arc[] arcs_31_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_31_2 = {
        new Arc(GramInit.labels[23], GramInit.states_31[1]),
    };

    public static Arc[] arcs_31_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[85], GramInit.states_31[2]),
    };

    public static State[] states_31 = {
        new State(1, GramInit.arcs_31_0),
        new State(1, GramInit.arcs_31_1),
        new State(1, GramInit.arcs_31_2),
        new State(2, GramInit.arcs_31_3),
    };

    public static Arc[] arcs_32_0 = {
        new Arc(GramInit.labels[84], GramInit.states_32[1]),
    };

    public static Arc[] arcs_32_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_32[2]),
    };

    public static Arc[] arcs_32_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[84], GramInit.states_32[1]),
    };

    public static State[] states_32 = {
        new State(1, GramInit.arcs_32_0),
        new State(2, GramInit.arcs_32_1),
        new State(2, GramInit.arcs_32_2),
    };

    public static Arc[] arcs_33_0 = {
        new Arc(GramInit.labels[86], GramInit.states_33[1]),
    };

    public static Arc[] arcs_33_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_33[0]),
    };

    public static State[] states_33 = {
        new State(1, GramInit.arcs_33_0),
        new State(2, GramInit.arcs_33_1),
    };

    public static Arc[] arcs_34_0 = {
        new Arc(GramInit.labels[23], GramInit.states_34[1]),
    };

    public static Arc[] arcs_34_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[81], GramInit.states_34[0]),
    };

    public static State[] states_34 = {
        new State(1, GramInit.arcs_34_0),
        new State(2, GramInit.arcs_34_1),
    };

    public static Arc[] arcs_35_0 = {
        new Arc(GramInit.labels[87], GramInit.states_35[1]),
    };

    public static Arc[] arcs_35_1 = {
        new Arc(GramInit.labels[23], GramInit.states_35[2]),
    };

    public static Arc[] arcs_35_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_35[1]),
    };

    public static State[] states_35 = {
        new State(1, GramInit.arcs_35_0),
        new State(1, GramInit.arcs_35_1),
        new State(2, GramInit.arcs_35_2),
    };

    public static Arc[] arcs_36_0 = {
        new Arc(GramInit.labels[88], GramInit.states_36[2]),
    };

    public static Arc[] arcs_36_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_36[2]),
    };

    public static Arc[] arcs_36_2 = {
        new Arc(GramInit.labels[23], GramInit.states_36[1]),
    };

    public static State[] states_36 = {
        new State(1, GramInit.arcs_36_0),
        new State(2, GramInit.arcs_36_1),
        new State(1, GramInit.arcs_36_2),
    };

    public static Arc[] arcs_37_0 = {
        new Arc(GramInit.labels[89], GramInit.states_37[2]),
    };

    public static Arc[] arcs_37_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_37[4]),
    };

    public static Arc[] arcs_37_2 = {
        new Arc(GramInit.labels[26], GramInit.states_37[1]),
    };

    public static Arc[] arcs_37_3 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_37_4 = {
        new Arc(GramInit.labels[26], GramInit.states_37[3]),
    };

    public static State[] states_37 = {
        new State(1, GramInit.arcs_37_0),
        new State(2, GramInit.arcs_37_1),
        new State(1, GramInit.arcs_37_2),
        new State(1, GramInit.arcs_37_3),
        new State(1, GramInit.arcs_37_4),
    };

    public static Arc[] arcs_38_0 = {
        new Arc(GramInit.labels[92], GramInit.states_38[1]),
        new Arc(GramInit.labels[19], GramInit.states_38[1]),
        new Arc(GramInit.labels[91], GramInit.states_38[1]),
        new Arc(GramInit.labels[90], GramInit.states_38[1]),
        new Arc(GramInit.labels[94], GramInit.states_38[1]),
        new Arc(GramInit.labels[17], GramInit.states_38[1]),
        new Arc(GramInit.labels[18], GramInit.states_38[1]),
        new Arc(GramInit.labels[95], GramInit.states_38[1]),
        new Arc(GramInit.labels[93], GramInit.states_38[1]),
    };

    public static Arc[] arcs_38_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_38 = {
        new State(9, GramInit.arcs_38_0),
        new State(1, GramInit.arcs_38_1),
    };

    public static Arc[] arcs_39_0 = {
        new Arc(GramInit.labels[21], GramInit.states_39[2]),
    };

    public static Arc[] arcs_39_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_39_2 = {
        new Arc(GramInit.labels[92], GramInit.states_39[1]),
        new Arc(GramInit.labels[19], GramInit.states_39[1]),
        new Arc(GramInit.labels[94], GramInit.states_39[1]),
    };

    public static State[] states_39 = {
        new State(1, GramInit.arcs_39_0),
        new State(1, GramInit.arcs_39_1),
        new State(3, GramInit.arcs_39_2),
    };

    public static Arc[] arcs_40_0 = {
        new Arc(GramInit.labels[96], GramInit.states_40[3]),
    };

    public static Arc[] arcs_40_1 = {
        new Arc(GramInit.labels[27], GramInit.states_40[2]),
    };

    public static Arc[] arcs_40_2 = {
        new Arc(GramInit.labels[28], GramInit.states_40[4]),
    };

    public static Arc[] arcs_40_3 = {
        new Arc(GramInit.labels[26], GramInit.states_40[1]),
    };

    public static Arc[] arcs_40_4 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[98], GramInit.states_40[1]),
        new Arc(GramInit.labels[97], GramInit.states_40[3]),
    };

    public static State[] states_40 = {
        new State(1, GramInit.arcs_40_0),
        new State(1, GramInit.arcs_40_1),
        new State(1, GramInit.arcs_40_2),
        new State(1, GramInit.arcs_40_3),
        new State(3, GramInit.arcs_40_4),
    };

    public static Arc[] arcs_41_0 = {
        new Arc(GramInit.labels[99], GramInit.states_41[4]),
    };

    public static Arc[] arcs_41_1 = {
        new Arc(GramInit.labels[28], GramInit.states_41[3]),
    };

    public static Arc[] arcs_41_2 = {
        new Arc(GramInit.labels[27], GramInit.states_41[1]),
    };

    public static Arc[] arcs_41_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[98], GramInit.states_41[2]),
    };

    public static Arc[] arcs_41_4 = {
        new Arc(GramInit.labels[26], GramInit.states_41[2]),
    };

    public static State[] states_41 = {
        new State(1, GramInit.arcs_41_0),
        new State(1, GramInit.arcs_41_1),
        new State(1, GramInit.arcs_41_2),
        new State(2, GramInit.arcs_41_3),
        new State(1, GramInit.arcs_41_4),
    };

    public static Arc[] arcs_42_0 = {
        new Arc(GramInit.labels[100], GramInit.states_42[1]),
    };

    public static Arc[] arcs_42_1 = {
        new Arc(GramInit.labels[65], GramInit.states_42[2]),
    };

    public static Arc[] arcs_42_2 = {
        new Arc(GramInit.labels[101], GramInit.states_42[6]),
    };

    public static Arc[] arcs_42_3 = {
        new Arc(GramInit.labels[27], GramInit.states_42[5]),
    };

    public static Arc[] arcs_42_4 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_42_5 = {
        new Arc(GramInit.labels[28], GramInit.states_42[4]),
    };

    public static Arc[] arcs_42_6 = {
        new Arc(GramInit.labels[9], GramInit.states_42[3]),
    };

    public static State[] states_42 = {
        new State(1, GramInit.arcs_42_0),
        new State(1, GramInit.arcs_42_1),
        new State(1, GramInit.arcs_42_2),
        new State(1, GramInit.arcs_42_3),
        new State(1, GramInit.arcs_42_4),
        new State(1, GramInit.arcs_42_5),
        new State(1, GramInit.arcs_42_6),
    };

    public static Arc[] arcs_43_0 = {
        new Arc(GramInit.labels[102], GramInit.states_43[9]),
    };

    public static Arc[] arcs_43_1 = {
        new Arc(GramInit.labels[104], GramInit.states_43[4]),
        new Arc(GramInit.labels[103], GramInit.states_43[2]),
    };

    public static Arc[] arcs_43_2 = {
        new Arc(GramInit.labels[27], GramInit.states_43[5]),
    };

    public static Arc[] arcs_43_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[98], GramInit.states_43[2]),
        new Arc(GramInit.labels[104], GramInit.states_43[4]),
        new Arc(GramInit.labels[103], GramInit.states_43[2]),
    };

    public static Arc[] arcs_43_4 = {
        new Arc(GramInit.labels[27], GramInit.states_43[6]),
    };

    public static Arc[] arcs_43_5 = {
        new Arc(GramInit.labels[28], GramInit.states_43[3]),
    };

    public static Arc[] arcs_43_6 = {
        new Arc(GramInit.labels[28], GramInit.states_43[8]),
    };

    public static Arc[] arcs_43_7 = {
        new Arc(GramInit.labels[28], GramInit.states_43[1]),
    };

    public static Arc[] arcs_43_8 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_43_9 = {
        new Arc(GramInit.labels[27], GramInit.states_43[7]),
    };

    public static State[] states_43 = {
        new State(1, GramInit.arcs_43_0),
        new State(2, GramInit.arcs_43_1),
        new State(1, GramInit.arcs_43_2),
        new State(4, GramInit.arcs_43_3),
        new State(1, GramInit.arcs_43_4),
        new State(1, GramInit.arcs_43_5),
        new State(1, GramInit.arcs_43_6),
        new State(1, GramInit.arcs_43_7),
        new State(1, GramInit.arcs_43_8),
        new State(1, GramInit.arcs_43_9),
    };

    public static Arc[] arcs_44_0 = {
        new Arc(GramInit.labels[105], GramInit.states_44[1]),
    };

    public static Arc[] arcs_44_1 = {
        new Arc(GramInit.labels[106], GramInit.states_44[3]),
    };

    public static Arc[] arcs_44_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_44_3 = {
        new Arc(GramInit.labels[27], GramInit.states_44[4]),
        new Arc(GramInit.labels[32], GramInit.states_44[1]),
    };

    public static Arc[] arcs_44_4 = {
        new Arc(GramInit.labels[28], GramInit.states_44[2]),
    };

    public static State[] states_44 = {
        new State(1, GramInit.arcs_44_0),
        new State(1, GramInit.arcs_44_1),
        new State(1, GramInit.arcs_44_2),
        new State(2, GramInit.arcs_44_3),
        new State(1, GramInit.arcs_44_4),
    };

    public static Arc[] arcs_45_0 = {
        new Arc(GramInit.labels[26], GramInit.states_45[2]),
    };

    public static Arc[] arcs_45_1 = {
        new Arc(GramInit.labels[107], GramInit.states_45[3]),
    };

    public static Arc[] arcs_45_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[85], GramInit.states_45[1]),
    };

    public static Arc[] arcs_45_3 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_45 = {
        new State(1, GramInit.arcs_45_0),
        new State(1, GramInit.arcs_45_1),
        new State(2, GramInit.arcs_45_2),
        new State(1, GramInit.arcs_45_3),
    };

    public static Arc[] arcs_46_0 = {
        new Arc(GramInit.labels[108], GramInit.states_46[3]),
    };

    public static Arc[] arcs_46_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[85], GramInit.states_46[4]),
    };

    public static Arc[] arcs_46_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_46_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[26], GramInit.states_46[1]),
    };

    public static Arc[] arcs_46_4 = {
        new Arc(GramInit.labels[23], GramInit.states_46[2]),
    };

    public static State[] states_46 = {
        new State(1, GramInit.arcs_46_0),
        new State(2, GramInit.arcs_46_1),
        new State(1, GramInit.arcs_46_2),
        new State(2, GramInit.arcs_46_3),
        new State(1, GramInit.arcs_46_4),
    };

    public static Arc[] arcs_47_0 = {
        new Arc(GramInit.labels[2], GramInit.states_47[2]),
        new Arc(GramInit.labels[3], GramInit.states_47[1]),
    };

    public static Arc[] arcs_47_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_47_2 = {
        new Arc(GramInit.labels[109], GramInit.states_47[4]),
    };

    public static Arc[] arcs_47_3 = {
        new Arc(GramInit.labels[110], GramInit.states_47[1]),
        new Arc(GramInit.labels[6], GramInit.states_47[3]),
    };

    public static Arc[] arcs_47_4 = {
        new Arc(GramInit.labels[6], GramInit.states_47[3]),
    };

    public static State[] states_47 = {
        new State(2, GramInit.arcs_47_0),
        new State(1, GramInit.arcs_47_1),
        new State(1, GramInit.arcs_47_2),
        new State(2, GramInit.arcs_47_3),
        new State(1, GramInit.arcs_47_4),
    };

    public static Arc[] arcs_48_0 = {
        new Arc(GramInit.labels[112], GramInit.states_48[3]),
        new Arc(GramInit.labels[111], GramInit.states_48[2]),
    };

    public static Arc[] arcs_48_1 = {
        new Arc(GramInit.labels[98], GramInit.states_48[4]),
    };

    public static Arc[] arcs_48_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[96], GramInit.states_48[5]),
    };

    public static Arc[] arcs_48_3 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_48_4 = {
        new Arc(GramInit.labels[26], GramInit.states_48[3]),
    };

    public static Arc[] arcs_48_5 = {
        new Arc(GramInit.labels[111], GramInit.states_48[1]),
    };

    public static State[] states_48 = {
        new State(2, GramInit.arcs_48_0),
        new State(1, GramInit.arcs_48_1),
        new State(2, GramInit.arcs_48_2),
        new State(1, GramInit.arcs_48_3),
        new State(1, GramInit.arcs_48_4),
        new State(1, GramInit.arcs_48_5),
    };

    public static Arc[] arcs_49_0 = {
        new Arc(GramInit.labels[114], GramInit.states_49[1]),
        new Arc(GramInit.labels[111], GramInit.states_49[1]),
    };

    public static Arc[] arcs_49_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_49 = {
        new State(2, GramInit.arcs_49_0),
        new State(1, GramInit.arcs_49_1),
    };

    public static Arc[] arcs_50_0 = {
        new Arc(GramInit.labels[115], GramInit.states_50[3]),
    };

    public static Arc[] arcs_50_1 = {
        new Arc(GramInit.labels[27], GramInit.states_50[2]),
    };

    public static Arc[] arcs_50_2 = {
        new Arc(GramInit.labels[26], GramInit.states_50[4]),
    };

    public static Arc[] arcs_50_3 = {
        new Arc(GramInit.labels[35], GramInit.states_50[1]),
        new Arc(GramInit.labels[27], GramInit.states_50[2]),
    };

    public static Arc[] arcs_50_4 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_50 = {
        new State(1, GramInit.arcs_50_0),
        new State(1, GramInit.arcs_50_1),
        new State(1, GramInit.arcs_50_2),
        new State(2, GramInit.arcs_50_3),
        new State(1, GramInit.arcs_50_4),
    };

    public static Arc[] arcs_51_0 = {
        new Arc(GramInit.labels[115], GramInit.states_51[2]),
    };

    public static Arc[] arcs_51_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_51_2 = {
        new Arc(GramInit.labels[35], GramInit.states_51[4]),
        new Arc(GramInit.labels[27], GramInit.states_51[3]),
    };

    public static Arc[] arcs_51_3 = {
        new Arc(GramInit.labels[113], GramInit.states_51[1]),
    };

    public static Arc[] arcs_51_4 = {
        new Arc(GramInit.labels[27], GramInit.states_51[3]),
    };

    public static State[] states_51 = {
        new State(1, GramInit.arcs_51_0),
        new State(1, GramInit.arcs_51_1),
        new State(2, GramInit.arcs_51_2),
        new State(1, GramInit.arcs_51_3),
        new State(1, GramInit.arcs_51_4),
    };

    public static Arc[] arcs_52_0 = {
        new Arc(GramInit.labels[116], GramInit.states_52[1]),
    };

    public static Arc[] arcs_52_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[117], GramInit.states_52[0]),
    };

    public static State[] states_52 = {
        new State(1, GramInit.arcs_52_0),
        new State(2, GramInit.arcs_52_1),
    };

    public static Arc[] arcs_53_0 = {
        new Arc(GramInit.labels[118], GramInit.states_53[1]),
    };

    public static Arc[] arcs_53_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[119], GramInit.states_53[0]),
    };

    public static State[] states_53 = {
        new State(1, GramInit.arcs_53_0),
        new State(2, GramInit.arcs_53_1),
    };

    public static Arc[] arcs_54_0 = {
        new Arc(GramInit.labels[121], GramInit.states_54[2]),
        new Arc(GramInit.labels[120], GramInit.states_54[1]),
    };

    public static Arc[] arcs_54_1 = {
        new Arc(GramInit.labels[118], GramInit.states_54[2]),
    };

    public static Arc[] arcs_54_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_54 = {
        new State(2, GramInit.arcs_54_0),
        new State(1, GramInit.arcs_54_1),
        new State(1, GramInit.arcs_54_2),
    };

    public static Arc[] arcs_55_0 = {
        new Arc(GramInit.labels[107], GramInit.states_55[1]),
    };

    public static Arc[] arcs_55_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[122], GramInit.states_55[0]),
    };

    public static State[] states_55 = {
        new State(1, GramInit.arcs_55_0),
        new State(2, GramInit.arcs_55_1),
    };

    public static Arc[] arcs_56_0 = {
        new Arc(GramInit.labels[129], GramInit.states_56[1]),
        new Arc(GramInit.labels[101], GramInit.states_56[2]),
        new Arc(GramInit.labels[126], GramInit.states_56[2]),
        new Arc(GramInit.labels[124], GramInit.states_56[2]),
        new Arc(GramInit.labels[128], GramInit.states_56[2]),
        new Arc(GramInit.labels[125], GramInit.states_56[2]),
        new Arc(GramInit.labels[120], GramInit.states_56[3]),
        new Arc(GramInit.labels[127], GramInit.states_56[2]),
        new Arc(GramInit.labels[123], GramInit.states_56[2]),
    };

    public static Arc[] arcs_56_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[120], GramInit.states_56[2]),
    };

    public static Arc[] arcs_56_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_56_3 = {
        new Arc(GramInit.labels[101], GramInit.states_56[2]),
    };

    public static State[] states_56 = {
        new State(9, GramInit.arcs_56_0),
        new State(2, GramInit.arcs_56_1),
        new State(1, GramInit.arcs_56_2),
        new State(1, GramInit.arcs_56_3),
    };

    public static Arc[] arcs_57_0 = {
        new Arc(GramInit.labels[33], GramInit.states_57[2]),
    };

    public static Arc[] arcs_57_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_57_2 = {
        new Arc(GramInit.labels[107], GramInit.states_57[1]),
    };

    public static State[] states_57 = {
        new State(1, GramInit.arcs_57_0),
        new State(1, GramInit.arcs_57_1),
        new State(1, GramInit.arcs_57_2),
    };

    public static Arc[] arcs_58_0 = {
        new Arc(GramInit.labels[130], GramInit.states_58[1]),
    };

    public static Arc[] arcs_58_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[131], GramInit.states_58[0]),
    };

    public static State[] states_58 = {
        new State(1, GramInit.arcs_58_0),
        new State(2, GramInit.arcs_58_1),
    };

    public static Arc[] arcs_59_0 = {
        new Arc(GramInit.labels[132], GramInit.states_59[1]),
    };

    public static Arc[] arcs_59_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[133], GramInit.states_59[0]),
    };

    public static State[] states_59 = {
        new State(1, GramInit.arcs_59_0),
        new State(2, GramInit.arcs_59_1),
    };

    public static Arc[] arcs_60_0 = {
        new Arc(GramInit.labels[134], GramInit.states_60[1]),
    };

    public static Arc[] arcs_60_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[135], GramInit.states_60[0]),
    };

    public static State[] states_60 = {
        new State(1, GramInit.arcs_60_0),
        new State(2, GramInit.arcs_60_1),
    };

    public static Arc[] arcs_61_0 = {
        new Arc(GramInit.labels[136], GramInit.states_61[1]),
    };

    public static Arc[] arcs_61_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[137], GramInit.states_61[0]),
        new Arc(GramInit.labels[138], GramInit.states_61[0]),
    };

    public static State[] states_61 = {
        new State(1, GramInit.arcs_61_0),
        new State(3, GramInit.arcs_61_1),
    };

    public static Arc[] arcs_62_0 = {
        new Arc(GramInit.labels[139], GramInit.states_62[1]),
    };

    public static Arc[] arcs_62_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[141], GramInit.states_62[0]),
        new Arc(GramInit.labels[140], GramInit.states_62[0]),
    };

    public static State[] states_62 = {
        new State(1, GramInit.arcs_62_0),
        new State(3, GramInit.arcs_62_1),
    };

    public static Arc[] arcs_63_0 = {
        new Arc(GramInit.labels[142], GramInit.states_63[1]),
    };

    public static Arc[] arcs_63_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[11], GramInit.states_63[0]),
        new Arc(GramInit.labels[145], GramInit.states_63[0]),
        new Arc(GramInit.labels[143], GramInit.states_63[0]),
        new Arc(GramInit.labels[33], GramInit.states_63[0]),
        new Arc(GramInit.labels[144], GramInit.states_63[0]),
    };

    public static State[] states_63 = {
        new State(1, GramInit.arcs_63_0),
        new State(6, GramInit.arcs_63_1),
    };

    public static Arc[] arcs_64_0 = {
        new Arc(GramInit.labels[147], GramInit.states_64[1]),
        new Arc(GramInit.labels[146], GramInit.states_64[2]),
        new Arc(GramInit.labels[141], GramInit.states_64[2]),
        new Arc(GramInit.labels[140], GramInit.states_64[2]),
    };

    public static Arc[] arcs_64_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_64_2 = {
        new Arc(GramInit.labels[142], GramInit.states_64[1]),
    };

    public static State[] states_64 = {
        new State(4, GramInit.arcs_64_0),
        new State(1, GramInit.arcs_64_1),
        new State(1, GramInit.arcs_64_2),
    };

    public static Arc[] arcs_65_0 = {
        new Arc(GramInit.labels[148], GramInit.states_65[3]),
    };

    public static Arc[] arcs_65_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_65_2 = {
        new Arc(GramInit.labels[142], GramInit.states_65[1]),
    };

    public static Arc[] arcs_65_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[34], GramInit.states_65[2]),
    };

    public static State[] states_65 = {
        new State(1, GramInit.arcs_65_0),
        new State(1, GramInit.arcs_65_1),
        new State(1, GramInit.arcs_65_2),
        new State(2, GramInit.arcs_65_3),
    };

    public static Arc[] arcs_66_0 = {
        new Arc(GramInit.labels[150], GramInit.states_66[1]),
        new Arc(GramInit.labels[149], GramInit.states_66[2]),
    };

    public static Arc[] arcs_66_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[151], GramInit.states_66[1]),
    };

    public static Arc[] arcs_66_2 = {
        new Arc(GramInit.labels[150], GramInit.states_66[1]),
    };

    public static State[] states_66 = {
        new State(2, GramInit.arcs_66_0),
        new State(2, GramInit.arcs_66_1),
        new State(1, GramInit.arcs_66_2),
    };

    public static Arc[] arcs_67_0 = {
        new Arc(GramInit.labels[162], GramInit.states_67[6]),
        new Arc(GramInit.labels[160], GramInit.states_67[6]),
        new Arc(GramInit.labels[158], GramInit.states_67[6]),
        new Arc(GramInit.labels[155], GramInit.states_67[4]),
        new Arc(GramInit.labels[159], GramInit.states_67[1]),
        new Arc(GramInit.labels[161], GramInit.states_67[6]),
        new Arc(GramInit.labels[82], GramInit.states_67[6]),
        new Arc(GramInit.labels[153], GramInit.states_67[2]),
        new Arc(GramInit.labels[23], GramInit.states_67[6]),
        new Arc(GramInit.labels[13], GramInit.states_67[5]),
    };

    public static Arc[] arcs_67_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[159], GramInit.states_67[1]),
    };

    public static Arc[] arcs_67_2 = {
        new Arc(GramInit.labels[152], GramInit.states_67[3]),
        new Arc(GramInit.labels[154], GramInit.states_67[6]),
    };

    public static Arc[] arcs_67_3 = {
        new Arc(GramInit.labels[154], GramInit.states_67[6]),
    };

    public static Arc[] arcs_67_4 = {
        new Arc(GramInit.labels[157], GramInit.states_67[6]),
    };

    public static Arc[] arcs_67_5 = {
        new Arc(GramInit.labels[15], GramInit.states_67[6]),
        new Arc(GramInit.labels[152], GramInit.states_67[5]),
        new Arc(GramInit.labels[49], GramInit.states_67[5]),
    };

    public static Arc[] arcs_67_6 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_67 = {
        new State(10, GramInit.arcs_67_0),
        new State(2, GramInit.arcs_67_1),
        new State(2, GramInit.arcs_67_2),
        new State(1, GramInit.arcs_67_3),
        new State(1, GramInit.arcs_67_4),
        new State(3, GramInit.arcs_67_5),
        new State(1, GramInit.arcs_67_6),
    };

    public static Arc[] arcs_68_0 = {
        new Arc(GramInit.labels[50], GramInit.states_68[2]),
        new Arc(GramInit.labels[26], GramInit.states_68[2]),
    };

    public static Arc[] arcs_68_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[50], GramInit.states_68[3]),
        new Arc(GramInit.labels[26], GramInit.states_68[3]),
    };

    public static Arc[] arcs_68_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[163], GramInit.states_68[4]),
        new Arc(GramInit.labels[32], GramInit.states_68[1]),
    };

    public static Arc[] arcs_68_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_68[1]),
    };

    public static Arc[] arcs_68_4 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_68 = {
        new State(2, GramInit.arcs_68_0),
        new State(3, GramInit.arcs_68_1),
        new State(3, GramInit.arcs_68_2),
        new State(2, GramInit.arcs_68_3),
        new State(1, GramInit.arcs_68_4),
    };

    public static Arc[] arcs_69_0 = {
        new Arc(GramInit.labels[81], GramInit.states_69[3]),
        new Arc(GramInit.labels[153], GramInit.states_69[4]),
        new Arc(GramInit.labels[13], GramInit.states_69[6]),
    };

    public static Arc[] arcs_69_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_69_2 = {
        new Arc(GramInit.labels[154], GramInit.states_69[1]),
    };

    public static Arc[] arcs_69_3 = {
        new Arc(GramInit.labels[23], GramInit.states_69[1]),
    };

    public static Arc[] arcs_69_4 = {
        new Arc(GramInit.labels[164], GramInit.states_69[2]),
    };

    public static Arc[] arcs_69_5 = {
        new Arc(GramInit.labels[15], GramInit.states_69[1]),
    };

    public static Arc[] arcs_69_6 = {
        new Arc(GramInit.labels[15], GramInit.states_69[1]),
        new Arc(GramInit.labels[14], GramInit.states_69[5]),
    };

    public static State[] states_69 = {
        new State(3, GramInit.arcs_69_0),
        new State(1, GramInit.arcs_69_1),
        new State(1, GramInit.arcs_69_2),
        new State(1, GramInit.arcs_69_3),
        new State(1, GramInit.arcs_69_4),
        new State(1, GramInit.arcs_69_5),
        new State(2, GramInit.arcs_69_6),
    };

    public static Arc[] arcs_70_0 = {
        new Arc(GramInit.labels[165], GramInit.states_70[1]),
    };

    public static Arc[] arcs_70_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_70[2]),
    };

    public static Arc[] arcs_70_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[165], GramInit.states_70[1]),
    };

    public static State[] states_70 = {
        new State(1, GramInit.arcs_70_0),
        new State(2, GramInit.arcs_70_1),
        new State(2, GramInit.arcs_70_2),
    };

    public static Arc[] arcs_71_0 = {
        new Arc(GramInit.labels[27], GramInit.states_71[3]),
        new Arc(GramInit.labels[26], GramInit.states_71[4]),
    };

    public static Arc[] arcs_71_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[166], GramInit.states_71[2]),
    };

    public static Arc[] arcs_71_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_71_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[166], GramInit.states_71[2]),
        new Arc(GramInit.labels[26], GramInit.states_71[1]),
    };

    public static Arc[] arcs_71_4 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[27], GramInit.states_71[3]),
    };

    public static State[] states_71 = {
        new State(2, GramInit.arcs_71_0),
        new State(2, GramInit.arcs_71_1),
        new State(1, GramInit.arcs_71_2),
        new State(3, GramInit.arcs_71_3),
        new State(2, GramInit.arcs_71_4),
    };

    public static Arc[] arcs_72_0 = {
        new Arc(GramInit.labels[27], GramInit.states_72[1]),
    };

    public static Arc[] arcs_72_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[26], GramInit.states_72[2]),
    };

    public static Arc[] arcs_72_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_72 = {
        new State(1, GramInit.arcs_72_0),
        new State(2, GramInit.arcs_72_1),
        new State(1, GramInit.arcs_72_2),
    };

    public static Arc[] arcs_73_0 = {
        new Arc(GramInit.labels[107], GramInit.states_73[2]),
        new Arc(GramInit.labels[50], GramInit.states_73[2]),
    };

    public static Arc[] arcs_73_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[107], GramInit.states_73[2]),
        new Arc(GramInit.labels[50], GramInit.states_73[2]),
    };

    public static Arc[] arcs_73_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_73[1]),
    };

    public static State[] states_73 = {
        new State(2, GramInit.arcs_73_0),
        new State(3, GramInit.arcs_73_1),
        new State(2, GramInit.arcs_73_2),
    };

    public static Arc[] arcs_74_0 = {
        new Arc(GramInit.labels[26], GramInit.states_74[1]),
    };

    public static Arc[] arcs_74_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_74[2]),
    };

    public static Arc[] arcs_74_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[26], GramInit.states_74[1]),
    };

    public static State[] states_74 = {
        new State(1, GramInit.arcs_74_0),
        new State(2, GramInit.arcs_74_1),
        new State(2, GramInit.arcs_74_2),
    };

    public static Arc[] arcs_75_0 = {
        new Arc(GramInit.labels[50], GramInit.states_75[7]),
        new Arc(GramInit.labels[34], GramInit.states_75[2]),
        new Arc(GramInit.labels[26], GramInit.states_75[10]),
    };

    public static Arc[] arcs_75_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_75[13]),
    };

    public static Arc[] arcs_75_2 = {
        new Arc(GramInit.labels[107], GramInit.states_75[9]),
    };

    public static Arc[] arcs_75_3 = {
        new Arc(GramInit.labels[26], GramInit.states_75[5]),
    };

    public static Arc[] arcs_75_4 = {
        new Arc(GramInit.labels[27], GramInit.states_75[3]),
    };

    public static Arc[] arcs_75_5 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_75[12]),
    };

    public static Arc[] arcs_75_6 = {
        new Arc(GramInit.labels[107], GramInit.states_75[5]),
    };

    public static Arc[] arcs_75_7 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[163], GramInit.states_75[8]),
        new Arc(GramInit.labels[32], GramInit.states_75[13]),
    };

    public static Arc[] arcs_75_8 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_75_9 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[163], GramInit.states_75[8]),
        new Arc(GramInit.labels[32], GramInit.states_75[12]),
    };

    public static Arc[] arcs_75_10 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[27], GramInit.states_75[11]),
        new Arc(GramInit.labels[163], GramInit.states_75[8]),
        new Arc(GramInit.labels[32], GramInit.states_75[13]),
    };

    public static Arc[] arcs_75_11 = {
        new Arc(GramInit.labels[26], GramInit.states_75[9]),
    };

    public static Arc[] arcs_75_12 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[34], GramInit.states_75[6]),
        new Arc(GramInit.labels[26], GramInit.states_75[4]),
    };

    public static Arc[] arcs_75_13 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[50], GramInit.states_75[1]),
        new Arc(GramInit.labels[26], GramInit.states_75[1]),
    };

    public static State[] states_75 = {
        new State(3, GramInit.arcs_75_0),
        new State(2, GramInit.arcs_75_1),
        new State(1, GramInit.arcs_75_2),
        new State(1, GramInit.arcs_75_3),
        new State(1, GramInit.arcs_75_4),
        new State(2, GramInit.arcs_75_5),
        new State(1, GramInit.arcs_75_6),
        new State(3, GramInit.arcs_75_7),
        new State(1, GramInit.arcs_75_8),
        new State(3, GramInit.arcs_75_9),
        new State(4, GramInit.arcs_75_10),
        new State(1, GramInit.arcs_75_11),
        new State(3, GramInit.arcs_75_12),
        new State(3, GramInit.arcs_75_13),
    };

    public static Arc[] arcs_76_0 = {
        new Arc(GramInit.labels[167], GramInit.states_76[4]),
    };

    public static Arc[] arcs_76_1 = {
        new Arc(GramInit.labels[15], GramInit.states_76[2]),
        new Arc(GramInit.labels[14], GramInit.states_76[1]),
    };

    public static Arc[] arcs_76_2 = {
        new Arc(GramInit.labels[27], GramInit.states_76[5]),
        new Arc(GramInit.labels[13], GramInit.states_76[1]),
    };

    public static Arc[] arcs_76_3 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_76_4 = {
        new Arc(GramInit.labels[23], GramInit.states_76[2]),
    };

    public static Arc[] arcs_76_5 = {
        new Arc(GramInit.labels[28], GramInit.states_76[3]),
    };

    public static State[] states_76 = {
        new State(1, GramInit.arcs_76_0),
        new State(2, GramInit.arcs_76_1),
        new State(2, GramInit.arcs_76_2),
        new State(1, GramInit.arcs_76_3),
        new State(1, GramInit.arcs_76_4),
        new State(1, GramInit.arcs_76_5),
    };

    public static Arc[] arcs_77_0 = {
        new Arc(GramInit.labels[168], GramInit.states_77[2]),
    };

    public static Arc[] arcs_77_1 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[168], GramInit.states_77[2]),
    };

    public static Arc[] arcs_77_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[32], GramInit.states_77[1]),
    };

    public static State[] states_77 = {
        new State(1, GramInit.arcs_77_0),
        new State(2, GramInit.arcs_77_1),
        new State(2, GramInit.arcs_77_2),
    };

    public static Arc[] arcs_78_0 = {
        new Arc(GramInit.labels[34], GramInit.states_78[1]),
        new Arc(GramInit.labels[33], GramInit.states_78[1]),
        new Arc(GramInit.labels[26], GramInit.states_78[3]),
    };

    public static Arc[] arcs_78_1 = {
        new Arc(GramInit.labels[26], GramInit.states_78[2]),
    };

    public static Arc[] arcs_78_2 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_78_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[31], GramInit.states_78[1]),
        new Arc(GramInit.labels[163], GramInit.states_78[2]),
    };

    public static State[] states_78 = {
        new State(3, GramInit.arcs_78_0),
        new State(1, GramInit.arcs_78_1),
        new State(1, GramInit.arcs_78_2),
        new State(3, GramInit.arcs_78_3),
    };

    public static Arc[] arcs_79_0 = {
        new Arc(GramInit.labels[163], GramInit.states_79[1]),
        new Arc(GramInit.labels[170], GramInit.states_79[1]),
    };

    public static Arc[] arcs_79_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_79 = {
        new State(2, GramInit.arcs_79_0),
        new State(1, GramInit.arcs_79_1),
    };

    public static Arc[] arcs_80_0 = {
        new Arc(GramInit.labels[100], GramInit.states_80[5]),
    };

    public static Arc[] arcs_80_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_80_2 = {
        new Arc(GramInit.labels[101], GramInit.states_80[4]),
    };

    public static Arc[] arcs_80_3 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[169], GramInit.states_80[1]),
    };

    public static Arc[] arcs_80_4 = {
        new Arc(GramInit.labels[111], GramInit.states_80[3]),
    };

    public static Arc[] arcs_80_5 = {
        new Arc(GramInit.labels[65], GramInit.states_80[2]),
    };

    public static State[] states_80 = {
        new State(1, GramInit.arcs_80_0),
        new State(1, GramInit.arcs_80_1),
        new State(1, GramInit.arcs_80_2),
        new State(2, GramInit.arcs_80_3),
        new State(1, GramInit.arcs_80_4),
        new State(1, GramInit.arcs_80_5),
    };

    public static Arc[] arcs_81_0 = {
        new Arc(GramInit.labels[96], GramInit.states_81[3]),
    };

    public static Arc[] arcs_81_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_81_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[169], GramInit.states_81[1]),
    };

    public static Arc[] arcs_81_3 = {
        new Arc(GramInit.labels[113], GramInit.states_81[2]),
    };

    public static State[] states_81 = {
        new State(1, GramInit.arcs_81_0),
        new State(1, GramInit.arcs_81_1),
        new State(2, GramInit.arcs_81_2),
        new State(1, GramInit.arcs_81_3),
    };

    public static Arc[] arcs_82_0 = {
        new Arc(GramInit.labels[23], GramInit.states_82[1]),
    };

    public static Arc[] arcs_82_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static State[] states_82 = {
        new State(1, GramInit.arcs_82_0),
        new State(1, GramInit.arcs_82_1),
    };

    public static Arc[] arcs_83_0 = {
        new Arc(GramInit.labels[172], GramInit.states_83[2]),
    };

    public static Arc[] arcs_83_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_83_2 = {
        new Arc(GramInit.labels[1], null),
        new Arc(GramInit.labels[173], GramInit.states_83[1]),
    };

    public static State[] states_83 = {
        new State(1, GramInit.arcs_83_0),
        new State(1, GramInit.arcs_83_1),
        new State(2, GramInit.arcs_83_2),
    };

    public static Arc[] arcs_84_0 = {
        new Arc(GramInit.labels[9], GramInit.states_84[1]),
        new Arc(GramInit.labels[76], GramInit.states_84[2]),
    };

    public static Arc[] arcs_84_1 = {
        new Arc(GramInit.labels[1], null),
    };

    public static Arc[] arcs_84_2 = {
        new Arc(GramInit.labels[26], GramInit.states_84[1]),
    };

    public static State[] states_84 = {
        new State(2, GramInit.arcs_84_0),
        new State(1, GramInit.arcs_84_1),
        new State(1, GramInit.arcs_84_2),
    };

    public static Map<Label, DFA> jumpedDFAs_0 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[22], dfas[7]);
        put(GramInit.labels[2], dfas[0]);
        put(GramInit.labels[79], dfas[28]);
        put(GramInit.labels[75], dfas[26]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[66], dfas[20]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[89], dfas[37]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[172], dfas[83]);
        put(GramInit.labels[74], dfas[24]);
        put(GramInit.labels[13], dfas[67]);
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[88], dfas[36]);
        put(GramInit.labels[167], dfas[76]);
        put(GramInit.labels[105], dfas[44]);
        put(GramInit.labels[72], dfas[22]);
        put(GramInit.labels[7], dfas[0]);
        put(GramInit.labels[100], dfas[42]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[73], dfas[23]);
        put(GramInit.labels[11], dfas[3]);
        put(GramInit.labels[87], dfas[35]);
        put(GramInit.labels[64], dfas[19]);
        put(GramInit.labels[96], dfas[40]);
        put(GramInit.labels[102], dfas[43]);
        put(GramInit.labels[21], dfas[39]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[99], dfas[41]);
        put(GramInit.labels[76], dfas[29]);
    }};

    public static Map<Label, DFA> jumpedDFAs_1 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[22], dfas[7]);
        put(GramInit.labels[2], dfas[1]);
        put(GramInit.labels[79], dfas[28]);
        put(GramInit.labels[75], dfas[26]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[66], dfas[20]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[89], dfas[37]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[172], dfas[83]);
        put(GramInit.labels[74], dfas[24]);
        put(GramInit.labels[13], dfas[67]);
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[88], dfas[36]);
        put(GramInit.labels[167], dfas[76]);
        put(GramInit.labels[105], dfas[44]);
        put(GramInit.labels[72], dfas[22]);
        put(GramInit.labels[100], dfas[42]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[73], dfas[23]);
        put(GramInit.labels[11], dfas[3]);
        put(GramInit.labels[87], dfas[35]);
        put(GramInit.labels[64], dfas[19]);
        put(GramInit.labels[96], dfas[40]);
        put(GramInit.labels[102], dfas[43]);
        put(GramInit.labels[21], dfas[39]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[99], dfas[41]);
        put(GramInit.labels[76], dfas[29]);
    }};

    public static Map<Label, DFA> jumpedDFAs_2 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_3 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[11], dfas[3]);
    }};

    public static Map<Label, DFA> jumpedDFAs_4 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[11], dfas[3]);
    }};

    public static Map<Label, DFA> jumpedDFAs_5 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[11], dfas[3]);
    }};

    public static Map<Label, DFA> jumpedDFAs_6 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[21], dfas[6]);
    }};

    public static Map<Label, DFA> jumpedDFAs_7 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[22], dfas[7]);
    }};

    public static Map<Label, DFA> jumpedDFAs_8 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[13], dfas[8]);
    }};

    public static Map<Label, DFA> jumpedDFAs_9 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[33], dfas[9]);
        put(GramInit.labels[23], dfas[10]);
        put(GramInit.labels[34], dfas[9]);
    }};

    public static Map<Label, DFA> jumpedDFAs_10 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[23], dfas[10]);
    }};

    public static Map<Label, DFA> jumpedDFAs_11 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[33], dfas[11]);
        put(GramInit.labels[23], dfas[12]);
        put(GramInit.labels[34], dfas[11]);
    }};

    public static Map<Label, DFA> jumpedDFAs_12 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[23], dfas[12]);
    }};

    public static Map<Label, DFA> jumpedDFAs_13 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[22], dfas[7]);
        put(GramInit.labels[79], dfas[28]);
        put(GramInit.labels[75], dfas[26]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[66], dfas[20]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[89], dfas[37]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[172], dfas[83]);
        put(GramInit.labels[74], dfas[24]);
        put(GramInit.labels[13], dfas[67]);
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[88], dfas[36]);
        put(GramInit.labels[167], dfas[76]);
        put(GramInit.labels[105], dfas[44]);
        put(GramInit.labels[72], dfas[22]);
        put(GramInit.labels[100], dfas[42]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[73], dfas[23]);
        put(GramInit.labels[11], dfas[3]);
        put(GramInit.labels[87], dfas[35]);
        put(GramInit.labels[64], dfas[19]);
        put(GramInit.labels[96], dfas[40]);
        put(GramInit.labels[102], dfas[43]);
        put(GramInit.labels[21], dfas[39]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[99], dfas[41]);
        put(GramInit.labels[76], dfas[29]);
    }};

    public static Map<Label, DFA> jumpedDFAs_14 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[79], dfas[28]);
        put(GramInit.labels[75], dfas[26]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[66], dfas[20]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[89], dfas[37]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[172], dfas[83]);
        put(GramInit.labels[74], dfas[24]);
        put(GramInit.labels[13], dfas[67]);
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[88], dfas[36]);
        put(GramInit.labels[72], dfas[22]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[73], dfas[23]);
        put(GramInit.labels[87], dfas[35]);
        put(GramInit.labels[64], dfas[19]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[76], dfas[29]);
    }};

    public static Map<Label, DFA> jumpedDFAs_15 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[79], dfas[28]);
        put(GramInit.labels[75], dfas[26]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[66], dfas[20]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[89], dfas[37]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[172], dfas[83]);
        put(GramInit.labels[74], dfas[24]);
        put(GramInit.labels[13], dfas[67]);
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[88], dfas[36]);
        put(GramInit.labels[72], dfas[22]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[73], dfas[23]);
        put(GramInit.labels[87], dfas[35]);
        put(GramInit.labels[64], dfas[19]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[76], dfas[29]);
    }};

    public static Map<Label, DFA> jumpedDFAs_16 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_17 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_18 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[61], dfas[18]);
        put(GramInit.labels[59], dfas[18]);
        put(GramInit.labels[62], dfas[18]);
        put(GramInit.labels[60], dfas[18]);
        put(GramInit.labels[56], dfas[18]);
        put(GramInit.labels[57], dfas[18]);
        put(GramInit.labels[53], dfas[18]);
        put(GramInit.labels[55], dfas[18]);
        put(GramInit.labels[63], dfas[18]);
        put(GramInit.labels[54], dfas[18]);
        put(GramInit.labels[52], dfas[18]);
        put(GramInit.labels[58], dfas[18]);
        put(GramInit.labels[51], dfas[18]);
    }};

    public static Map<Label, DFA> jumpedDFAs_19 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[64], dfas[19]);
    }};

    public static Map<Label, DFA> jumpedDFAs_20 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[66], dfas[20]);
    }};

    public static Map<Label, DFA> jumpedDFAs_21 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[72], dfas[22]);
        put(GramInit.labels[172], dfas[83]);
        put(GramInit.labels[74], dfas[24]);
        put(GramInit.labels[75], dfas[26]);
        put(GramInit.labels[73], dfas[23]);
    }};

    public static Map<Label, DFA> jumpedDFAs_22 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[72], dfas[22]);
    }};

    public static Map<Label, DFA> jumpedDFAs_23 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[73], dfas[23]);
    }};

    public static Map<Label, DFA> jumpedDFAs_24 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[74], dfas[24]);
    }};

    public static Map<Label, DFA> jumpedDFAs_25 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[172], dfas[83]);
    }};

    public static Map<Label, DFA> jumpedDFAs_26 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[75], dfas[26]);
    }};

    public static Map<Label, DFA> jumpedDFAs_27 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[76], dfas[29]);
        put(GramInit.labels[79], dfas[28]);
    }};

    public static Map<Label, DFA> jumpedDFAs_28 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[79], dfas[28]);
    }};

    public static Map<Label, DFA> jumpedDFAs_29 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[76], dfas[29]);
    }};

    public static Map<Label, DFA> jumpedDFAs_30 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[23], dfas[30]);
    }};

    public static Map<Label, DFA> jumpedDFAs_31 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[23], dfas[34]);
    }};

    public static Map<Label, DFA> jumpedDFAs_32 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[23], dfas[30]);
    }};

    public static Map<Label, DFA> jumpedDFAs_33 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[23], dfas[34]);
    }};

    public static Map<Label, DFA> jumpedDFAs_34 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[23], dfas[34]);
    }};

    public static Map<Label, DFA> jumpedDFAs_35 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[87], dfas[35]);
    }};

    public static Map<Label, DFA> jumpedDFAs_36 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[88], dfas[36]);
    }};

    public static Map<Label, DFA> jumpedDFAs_37 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[89], dfas[37]);
    }};

    public static Map<Label, DFA> jumpedDFAs_38 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[22], dfas[7]);
        put(GramInit.labels[11], dfas[3]);
        put(GramInit.labels[167], dfas[76]);
        put(GramInit.labels[105], dfas[44]);
        put(GramInit.labels[96], dfas[40]);
        put(GramInit.labels[102], dfas[43]);
        put(GramInit.labels[21], dfas[39]);
        put(GramInit.labels[100], dfas[42]);
        put(GramInit.labels[99], dfas[41]);
    }};

    public static Map<Label, DFA> jumpedDFAs_39 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[21], dfas[39]);
    }};

    public static Map<Label, DFA> jumpedDFAs_40 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[96], dfas[40]);
    }};

    public static Map<Label, DFA> jumpedDFAs_41 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[99], dfas[41]);
    }};

    public static Map<Label, DFA> jumpedDFAs_42 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[100], dfas[42]);
    }};

    public static Map<Label, DFA> jumpedDFAs_43 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[102], dfas[43]);
    }};

    public static Map<Label, DFA> jumpedDFAs_44 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[105], dfas[44]);
    }};

    public static Map<Label, DFA> jumpedDFAs_45 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_46 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[108], dfas[46]);
    }};

    public static Map<Label, DFA> jumpedDFAs_47 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[2], dfas[47]);
        put(GramInit.labels[79], dfas[28]);
        put(GramInit.labels[75], dfas[26]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[66], dfas[20]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[89], dfas[37]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[172], dfas[83]);
        put(GramInit.labels[74], dfas[24]);
        put(GramInit.labels[13], dfas[67]);
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[88], dfas[36]);
        put(GramInit.labels[72], dfas[22]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[73], dfas[23]);
        put(GramInit.labels[87], dfas[35]);
        put(GramInit.labels[64], dfas[19]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[76], dfas[29]);
    }};

    public static Map<Label, DFA> jumpedDFAs_48 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_49 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[51]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_50 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[115], dfas[50]);
    }};

    public static Map<Label, DFA> jumpedDFAs_51 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[115], dfas[51]);
    }};

    public static Map<Label, DFA> jumpedDFAs_52 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_53 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_54 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_55 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_56 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[129], dfas[56]);
        put(GramInit.labels[101], dfas[56]);
        put(GramInit.labels[126], dfas[56]);
        put(GramInit.labels[124], dfas[56]);
        put(GramInit.labels[128], dfas[56]);
        put(GramInit.labels[125], dfas[56]);
        put(GramInit.labels[120], dfas[56]);
        put(GramInit.labels[127], dfas[56]);
        put(GramInit.labels[123], dfas[56]);
    }};

    public static Map<Label, DFA> jumpedDFAs_57 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[33], dfas[57]);
    }};

    public static Map<Label, DFA> jumpedDFAs_58 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_59 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_60 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_61 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_62 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_63 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_64 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_65 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_66 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_67 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_68 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_69 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[81], dfas[69]);
        put(GramInit.labels[153], dfas[69]);
        put(GramInit.labels[13], dfas[69]);
    }};

    public static Map<Label, DFA> jumpedDFAs_70 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[27], dfas[71]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_71 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[27], dfas[71]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_72 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[27], dfas[72]);
    }};

    public static Map<Label, DFA> jumpedDFAs_73 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_74 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_75 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[57]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[34], dfas[75]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_76 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[167], dfas[76]);
    }};

    public static Map<Label, DFA> jumpedDFAs_77 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[78]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[34], dfas[78]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_78 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[33], dfas[78]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[34], dfas[78]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static Map<Label, DFA> jumpedDFAs_79 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[96], dfas[81]);
        put(GramInit.labels[100], dfas[80]);
    }};

    public static Map<Label, DFA> jumpedDFAs_80 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[100], dfas[80]);
    }};

    public static Map<Label, DFA> jumpedDFAs_81 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[96], dfas[81]);
    }};

    public static Map<Label, DFA> jumpedDFAs_82 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[23], dfas[82]);
    }};

    public static Map<Label, DFA> jumpedDFAs_83 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[172], dfas[83]);
    }};

    public static Map<Label, DFA> jumpedDFAs_84 = new HashMap<Label, DFA>(){{
        put(GramInit.labels[162], dfas[67]);
        put(GramInit.labels[155], dfas[67]);
        put(GramInit.labels[159], dfas[67]);
        put(GramInit.labels[149], dfas[66]);
        put(GramInit.labels[82], dfas[67]);
        put(GramInit.labels[153], dfas[67]);
        put(GramInit.labels[140], dfas[64]);
        put(GramInit.labels[23], dfas[67]);
        put(GramInit.labels[160], dfas[67]);
        put(GramInit.labels[158], dfas[67]);
        put(GramInit.labels[146], dfas[64]);
        put(GramInit.labels[115], dfas[50]);
        put(GramInit.labels[161], dfas[67]);
        put(GramInit.labels[120], dfas[54]);
        put(GramInit.labels[141], dfas[64]);
        put(GramInit.labels[76], dfas[84]);
        put(GramInit.labels[13], dfas[67]);
    }};

    public static DFA[] dfas = {
        new DFA(DFAName.file_input, GramInit.states_0[0], 2, GramInit.states_0, jumpedDFAs_0),
        new DFA(DFAName.single_input, GramInit.states_1[0], 3, GramInit.states_1, jumpedDFAs_1),
        new DFA(DFAName.eval_input, GramInit.states_2[0], 3, GramInit.states_2, jumpedDFAs_74),
        new DFA(DFAName.decorator, GramInit.states_3[0], 6, GramInit.states_3, jumpedDFAs_5),
        new DFA(DFAName.decorators, GramInit.states_4[0], 2, GramInit.states_4, jumpedDFAs_5),
        new DFA(DFAName.decorated, GramInit.states_5[0], 3, GramInit.states_5, jumpedDFAs_5),
        new DFA(DFAName.async_funcdef, GramInit.states_6[0], 3, GramInit.states_6, jumpedDFAs_6),
        new DFA(DFAName.funcdef, GramInit.states_7[0], 8, GramInit.states_7, jumpedDFAs_7),
        new DFA(DFAName.parameters, GramInit.states_8[0], 4, GramInit.states_8, jumpedDFAs_8),
        new DFA(DFAName.typedargslist, GramInit.states_9[0], 12, GramInit.states_9, jumpedDFAs_9),
        new DFA(DFAName.tfpdef, GramInit.states_10[0], 4, GramInit.states_10, jumpedDFAs_10),
        new DFA(DFAName.varargslist, GramInit.states_11[0], 12, GramInit.states_11, jumpedDFAs_11),
        new DFA(DFAName.vfpdef, GramInit.states_12[0], 2, GramInit.states_12, jumpedDFAs_12),
        new DFA(DFAName.stmt, GramInit.states_13[0], 2, GramInit.states_13, jumpedDFAs_13),
        new DFA(DFAName.simple_stmt, GramInit.states_14[0], 4, GramInit.states_14, jumpedDFAs_15),
        new DFA(DFAName.small_stmt, GramInit.states_15[0], 2, GramInit.states_15, jumpedDFAs_15),
        new DFA(DFAName.expr_stmt, GramInit.states_16[0], 6, GramInit.states_16, jumpedDFAs_68),
        new DFA(DFAName.testlist_star_expr, GramInit.states_17[0], 3, GramInit.states_17, jumpedDFAs_68),
        new DFA(DFAName.augassign, GramInit.states_18[0], 2, GramInit.states_18, jumpedDFAs_18),
        new DFA(DFAName.del_stmt, GramInit.states_19[0], 3, GramInit.states_19, jumpedDFAs_19),
        new DFA(DFAName.pass_stmt, GramInit.states_20[0], 2, GramInit.states_20, jumpedDFAs_20),
        new DFA(DFAName.flow_stmt, GramInit.states_21[0], 2, GramInit.states_21, jumpedDFAs_21),
        new DFA(DFAName.break_stmt, GramInit.states_22[0], 2, GramInit.states_22, jumpedDFAs_22),
        new DFA(DFAName.continue_stmt, GramInit.states_23[0], 2, GramInit.states_23, jumpedDFAs_23),
        new DFA(DFAName.return_stmt, GramInit.states_24[0], 3, GramInit.states_24, jumpedDFAs_24),
        new DFA(DFAName.yield_stmt, GramInit.states_25[0], 2, GramInit.states_25, jumpedDFAs_83),
        new DFA(DFAName.raise_stmt, GramInit.states_26[0], 5, GramInit.states_26, jumpedDFAs_26),
        new DFA(DFAName.import_stmt, GramInit.states_27[0], 2, GramInit.states_27, jumpedDFAs_27),
        new DFA(DFAName.import_name, GramInit.states_28[0], 3, GramInit.states_28, jumpedDFAs_28),
        new DFA(DFAName.import_from, GramInit.states_29[0], 8, GramInit.states_29, jumpedDFAs_29),
        new DFA(DFAName.import_as_name, GramInit.states_30[0], 2, GramInit.states_30, jumpedDFAs_32),
        new DFA(DFAName.dotted_as_name, GramInit.states_31[0], 4, GramInit.states_31, jumpedDFAs_34),
        new DFA(DFAName.import_as_names, GramInit.states_32[0], 3, GramInit.states_32, jumpedDFAs_32),
        new DFA(DFAName.dotted_as_names, GramInit.states_33[0], 2, GramInit.states_33, jumpedDFAs_34),
        new DFA(DFAName.dotted_name, GramInit.states_34[0], 2, GramInit.states_34, jumpedDFAs_34),
        new DFA(DFAName.global_stmt, GramInit.states_35[0], 3, GramInit.states_35, jumpedDFAs_35),
        new DFA(DFAName.nonlocal_stmt, GramInit.states_36[0], 3, GramInit.states_36, jumpedDFAs_36),
        new DFA(DFAName.assert_stmt, GramInit.states_37[0], 5, GramInit.states_37, jumpedDFAs_37),
        new DFA(DFAName.compound_stmt, GramInit.states_38[0], 2, GramInit.states_38, jumpedDFAs_38),
        new DFA(DFAName.async_stmt, GramInit.states_39[0], 3, GramInit.states_39, jumpedDFAs_39),
        new DFA(DFAName.if_stmt, GramInit.states_40[0], 5, GramInit.states_40, jumpedDFAs_40),
        new DFA(DFAName.while_stmt, GramInit.states_41[0], 5, GramInit.states_41, jumpedDFAs_41),
        new DFA(DFAName.for_stmt, GramInit.states_42[0], 7, GramInit.states_42, jumpedDFAs_42),
        new DFA(DFAName.try_stmt, GramInit.states_43[0], 10, GramInit.states_43, jumpedDFAs_43),
        new DFA(DFAName.with_stmt, GramInit.states_44[0], 5, GramInit.states_44, jumpedDFAs_44),
        new DFA(DFAName.with_item, GramInit.states_45[0], 4, GramInit.states_45, jumpedDFAs_74),
        new DFA(DFAName.except_clause, GramInit.states_46[0], 5, GramInit.states_46, jumpedDFAs_46),
        new DFA(DFAName.suite, GramInit.states_47[0], 5, GramInit.states_47, jumpedDFAs_47),
        new DFA(DFAName.test, GramInit.states_48[0], 6, GramInit.states_48, jumpedDFAs_74),
        new DFA(DFAName.test_nocond, GramInit.states_49[0], 2, GramInit.states_49, jumpedDFAs_49),
        new DFA(DFAName.lambdef, GramInit.states_50[0], 5, GramInit.states_50, jumpedDFAs_50),
        new DFA(DFAName.lambdef_nocond, GramInit.states_51[0], 5, GramInit.states_51, jumpedDFAs_51),
        new DFA(DFAName.or_test, GramInit.states_52[0], 2, GramInit.states_52, jumpedDFAs_54),
        new DFA(DFAName.and_test, GramInit.states_53[0], 2, GramInit.states_53, jumpedDFAs_54),
        new DFA(DFAName.not_test, GramInit.states_54[0], 3, GramInit.states_54, jumpedDFAs_54),
        new DFA(DFAName.comparison, GramInit.states_55[0], 2, GramInit.states_55, jumpedDFAs_64),
        new DFA(DFAName.comp_op, GramInit.states_56[0], 4, GramInit.states_56, jumpedDFAs_56),
        new DFA(DFAName.star_expr, GramInit.states_57[0], 3, GramInit.states_57, jumpedDFAs_57),
        new DFA(DFAName.expr, GramInit.states_58[0], 2, GramInit.states_58, jumpedDFAs_64),
        new DFA(DFAName.xor_expr, GramInit.states_59[0], 2, GramInit.states_59, jumpedDFAs_64),
        new DFA(DFAName.and_expr, GramInit.states_60[0], 2, GramInit.states_60, jumpedDFAs_64),
        new DFA(DFAName.shift_expr, GramInit.states_61[0], 2, GramInit.states_61, jumpedDFAs_64),
        new DFA(DFAName.arith_expr, GramInit.states_62[0], 2, GramInit.states_62, jumpedDFAs_64),
        new DFA(DFAName.term, GramInit.states_63[0], 2, GramInit.states_63, jumpedDFAs_64),
        new DFA(DFAName.factor, GramInit.states_64[0], 3, GramInit.states_64, jumpedDFAs_64),
        new DFA(DFAName.power, GramInit.states_65[0], 4, GramInit.states_65, jumpedDFAs_66),
        new DFA(DFAName.atom_expr, GramInit.states_66[0], 3, GramInit.states_66, jumpedDFAs_66),
        new DFA(DFAName.atom, GramInit.states_67[0], 7, GramInit.states_67, jumpedDFAs_67),
        new DFA(DFAName.testlist_comp, GramInit.states_68[0], 5, GramInit.states_68, jumpedDFAs_68),
        new DFA(DFAName.trailer, GramInit.states_69[0], 7, GramInit.states_69, jumpedDFAs_69),
        new DFA(DFAName.subscriptlist, GramInit.states_70[0], 3, GramInit.states_70, jumpedDFAs_71),
        new DFA(DFAName.subscript, GramInit.states_71[0], 5, GramInit.states_71, jumpedDFAs_71),
        new DFA(DFAName.sliceop, GramInit.states_72[0], 3, GramInit.states_72, jumpedDFAs_72),
        new DFA(DFAName.exprlist, GramInit.states_73[0], 3, GramInit.states_73, jumpedDFAs_73),
        new DFA(DFAName.testlist, GramInit.states_74[0], 3, GramInit.states_74, jumpedDFAs_74),
        new DFA(DFAName.dictorsetmaker, GramInit.states_75[0], 14, GramInit.states_75, jumpedDFAs_75),
        new DFA(DFAName.classdef, GramInit.states_76[0], 6, GramInit.states_76, jumpedDFAs_76),
        new DFA(DFAName.arglist, GramInit.states_77[0], 3, GramInit.states_77, jumpedDFAs_78),
        new DFA(DFAName.argument, GramInit.states_78[0], 4, GramInit.states_78, jumpedDFAs_78),
        new DFA(DFAName.comp_iter, GramInit.states_79[0], 2, GramInit.states_79, jumpedDFAs_79),
        new DFA(DFAName.comp_for, GramInit.states_80[0], 6, GramInit.states_80, jumpedDFAs_80),
        new DFA(DFAName.comp_if, GramInit.states_81[0], 4, GramInit.states_81, jumpedDFAs_81),
        new DFA(DFAName.encoding_decl, GramInit.states_82[0], 2, GramInit.states_82, jumpedDFAs_82),
        new DFA(DFAName.yield_expr, GramInit.states_83[0], 3, GramInit.states_83, jumpedDFAs_83),
        new DFA(DFAName.yield_arg, GramInit.states_84[0], 3, GramInit.states_84, jumpedDFAs_84),
    };

    public static Label[] labels = {
        new Label(dfas[1]),
        new Label(null),
        new Label(TokState.NEWLINE, null),
        new Label(dfas[14]),
        new Label(dfas[38]),
        new Label(dfas[0]),
        new Label(dfas[13]),
        new Label(TokState.ENDMARKER, null),
        new Label(dfas[2]),
        new Label(dfas[74]),
        new Label(dfas[3]),
        new Label(TokState.AT, null),
        new Label(dfas[34]),
        new Label(TokState.LPAR, null),
        new Label(dfas[77]),
        new Label(TokState.RPAR, null),
        new Label(dfas[4]),
        new Label(dfas[5]),
        new Label(dfas[76]),
        new Label(dfas[7]),
        new Label(dfas[6]),
        new Label(TokState.ASYNC, null),
        new Label(TokState.NAME, "def"),
        new Label(TokState.NAME, null),
        new Label(dfas[8]),
        new Label(TokState.RARROW, null),
        new Label(dfas[48]),
        new Label(TokState.COLON, null),
        new Label(dfas[47]),
        new Label(dfas[9]),
        new Label(dfas[10]),
        new Label(TokState.EQUAL, null),
        new Label(TokState.COMMA, null),
        new Label(TokState.STAR, null),
        new Label(TokState.DOUBLESTAR, null),
        new Label(dfas[11]),
        new Label(dfas[12]),
        new Label(dfas[15]),
        new Label(TokState.SEMI, null),
        new Label(dfas[16]),
        new Label(dfas[19]),
        new Label(dfas[20]),
        new Label(dfas[21]),
        new Label(dfas[27]),
        new Label(dfas[35]),
        new Label(dfas[36]),
        new Label(dfas[37]),
        new Label(dfas[17]),
        new Label(dfas[18]),
        new Label(dfas[83]),
        new Label(dfas[57]),
        new Label(TokState.PLUSEQUAL, null),
        new Label(TokState.MINEQUAL, null),
        new Label(TokState.STAREQUAL, null),
        new Label(TokState.ATEQUAL, null),
        new Label(TokState.SLASHEQUAL, null),
        new Label(TokState.PERCENTEQUAL, null),
        new Label(TokState.AMPEREQUAL, null),
        new Label(TokState.VBAREQUAL, null),
        new Label(TokState.CIRCUMFLEXEQUAL, null),
        new Label(TokState.LEFTSHIFTEQUAL, null),
        new Label(TokState.RIGHTSHIFTEQUAL, null),
        new Label(TokState.DOUBLESTAREQUAL, null),
        new Label(TokState.DOUBLESLASHEQUAL, null),
        new Label(TokState.NAME, "del"),
        new Label(dfas[73]),
        new Label(TokState.NAME, "pass"),
        new Label(dfas[22]),
        new Label(dfas[23]),
        new Label(dfas[24]),
        new Label(dfas[26]),
        new Label(dfas[25]),
        new Label(TokState.NAME, "break"),
        new Label(TokState.NAME, "continue"),
        new Label(TokState.NAME, "return"),
        new Label(TokState.NAME, "raise"),
        new Label(TokState.NAME, "from"),
        new Label(dfas[28]),
        new Label(dfas[29]),
        new Label(TokState.NAME, "import"),
        new Label(dfas[33]),
        new Label(TokState.DOT, null),
        new Label(TokState.ELLIPSIS, null),
        new Label(dfas[32]),
        new Label(dfas[30]),
        new Label(TokState.NAME, "as"),
        new Label(dfas[31]),
        new Label(TokState.NAME, "global"),
        new Label(TokState.NAME, "nonlocal"),
        new Label(TokState.NAME, "assert"),
        new Label(dfas[40]),
        new Label(dfas[41]),
        new Label(dfas[42]),
        new Label(dfas[43]),
        new Label(dfas[44]),
        new Label(dfas[39]),
        new Label(TokState.NAME, "if"),
        new Label(TokState.NAME, "elif"),
        new Label(TokState.NAME, "else"),
        new Label(TokState.NAME, "while"),
        new Label(TokState.NAME, "for"),
        new Label(TokState.NAME, "in"),
        new Label(TokState.NAME, "try"),
        new Label(dfas[46]),
        new Label(TokState.NAME, "finally"),
        new Label(TokState.NAME, "with"),
        new Label(dfas[45]),
        new Label(dfas[58]),
        new Label(TokState.NAME, "except"),
        new Label(TokState.INDENT, null),
        new Label(TokState.DEDENT, null),
        new Label(dfas[52]),
        new Label(dfas[50]),
        new Label(dfas[49]),
        new Label(dfas[51]),
        new Label(TokState.NAME, "lambda"),
        new Label(dfas[53]),
        new Label(TokState.NAME, "or"),
        new Label(dfas[54]),
        new Label(TokState.NAME, "and"),
        new Label(TokState.NAME, "not"),
        new Label(dfas[55]),
        new Label(dfas[56]),
        new Label(TokState.LESS, null),
        new Label(TokState.GREATER, null),
        new Label(TokState.EQEQUAL, null),
        new Label(TokState.GREATEREQUAL, null),
        new Label(TokState.LESSEQUAL, null),
        new Label(TokState.NOTEQUAL, null),
        new Label(TokState.NAME, "is"),
        new Label(dfas[59]),
        new Label(TokState.VBAR, null),
        new Label(dfas[60]),
        new Label(TokState.CIRCUMFLEX, null),
        new Label(dfas[61]),
        new Label(TokState.AMPER, null),
        new Label(dfas[62]),
        new Label(TokState.LEFTSHIFT, null),
        new Label(TokState.RIGHTSHIFT, null),
        new Label(dfas[63]),
        new Label(TokState.PLUS, null),
        new Label(TokState.MINUS, null),
        new Label(dfas[64]),
        new Label(TokState.SLASH, null),
        new Label(TokState.PERCENT, null),
        new Label(TokState.DOUBLESLASH, null),
        new Label(TokState.TILDE, null),
        new Label(dfas[65]),
        new Label(dfas[66]),
        new Label(TokState.AWAIT, null),
        new Label(dfas[67]),
        new Label(dfas[69]),
        new Label(dfas[68]),
        new Label(TokState.LSQB, null),
        new Label(TokState.RSQB, null),
        new Label(TokState.LBRACE, null),
        new Label(dfas[75]),
        new Label(TokState.RBRACE, null),
        new Label(TokState.NUMBER, null),
        new Label(TokState.STRING, null),
        new Label(TokState.NAME, "None"),
        new Label(TokState.NAME, "True"),
        new Label(TokState.NAME, "False"),
        new Label(dfas[80]),
        new Label(dfas[70]),
        new Label(dfas[71]),
        new Label(dfas[72]),
        new Label(TokState.NAME, "class"),
        new Label(dfas[78]),
        new Label(dfas[79]),
        new Label(dfas[81]),
        new Label(dfas[82]),
        new Label(TokState.NAME, "yield"),
        new Label(dfas[84]),
    };

    public static Grammar grammar = new Grammar(85, GramInit.dfas, 174, GramInit.labels, GramInit.dfas[0]);
};
