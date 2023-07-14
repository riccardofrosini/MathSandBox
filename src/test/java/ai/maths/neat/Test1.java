package ai.maths.neat;

import static ai.maths.neat.TicTacToeTrainer.fillUpEvaluationsAndPositions;
import static ai.maths.neat.TicTacToeTrainer.ticTacToeHashMap;
import static ai.maths.neat.TicTacToeTrainer.ticTacToeHashSet;

import java.io.File;
import java.io.IOException;

import ai.maths.neat.neuralnetwork.GenomeSerializerDeserializer;
import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.utils.NodeFunctionsCreator;
import ai.maths.tictactoe.TicTacToe;
import org.junit.Test;

public class Test1 {

    @Test
    public void test() throws IOException {
        String json = "[{\"inputId\":0,\"id\":1,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":1,\"id\":2,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":2,\"id\":3,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":3,\"id\":4,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":4,\"id\":5,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":5,\"id\":6,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":6,\"id\":7,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":7,\"id\":8,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":8,\"id\":9,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":9,\"id\":10,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":10,\"id\":11,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":11,\"id\":12,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":12,\"id\":13,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":13,\"id\":14,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":14,\"id\":15,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":15,\"id\":16,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":16,\"id\":17,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":17,\"id\":18,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":82,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":9,\"outNode\":82,\"innovation\":1389,\"weight\":0.19354122451925895,\"enabled\":true},{\"inNode\":2,\"outNode\":82,\"innovation\":3449,\"weight\":-0.5256395362335424,\"enabled\":true},{\"inNode\":26,\"outNode\":82,\"innovation\":4524,\"weight\":0.10379290809052666,\"enabled\":true},{\"inNode\":18,\"outNode\":82,\"innovation\":4654,\"weight\":0.5184532972223834,\"enabled\":true}]},{\"inputId\":18,\"id\":19,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":19,\"id\":20,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":20,\"id\":21,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":213,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":7,\"outNode\":213,\"innovation\":1684,\"weight\":-0.9664145261566064,\"enabled\":true},{\"inNode\":8,\"outNode\":213,\"innovation\":3346,\"weight\":-0.18159532111615384,\"enabled\":true},{\"inNode\":14,\"outNode\":213,\"innovation\":3848,\"weight\":0.8824699971662855,\"enabled\":true}]},{\"inputId\":21,\"id\":22,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":22,\"id\":23,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":23,\"id\":24,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":24,\"id\":25,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":25,\"id\":26,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":26,\"id\":27,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":28,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":1,\"outNode\":28,\"innovation\":403,\"weight\":0.8975103454915209,\"enabled\":true},{\"inNode\":16,\"outNode\":28,\"innovation\":926,\"weight\":0.3613982648073455,\"enabled\":true}]},{\"inputId\":0,\"id\":29,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":14,\"outNode\":29,\"innovation\":156,\"weight\":-0.28862403353631905,\"enabled\":true},{\"inNode\":5,\"outNode\":29,\"innovation\":187,\"weight\":-0.534732131479887,\"enabled\":true},{\"inNode\":3,\"outNode\":29,\"innovation\":597,\"weight\":0.27680701072793945,\"enabled\":true},{\"inNode\":15,\"outNode\":29,\"innovation\":802,\"weight\":-0.3816077927142117,\"enabled\":false},{\"inNode\":21,\"outNode\":29,\"innovation\":833,\"weight\":-0.6469773930792821,\"enabled\":true},{\"inNode\":25,\"outNode\":29,\"innovation\":921,\"weight\":0.2750459986614231,\"enabled\":true},{\"inNode\":7,\"outNode\":29,\"innovation\":1007,\"weight\":-0.038449154532656404,\"enabled\":true},{\"inNode\":17,\"outNode\":29,\"innovation\":1062,\"weight\":0.18269038350008127,\"enabled\":true},{\"inNode\":13,\"outNode\":29,\"innovation\":1088,\"weight\":-0.5875921648627768,\"enabled\":true},{\"inNode\":22,\"outNode\":29,\"innovation\":1114,\"weight\":-0.0733006793117037,\"enabled\":true},{\"inNode\":26,\"outNode\":29,\"innovation\":1160,\"weight\":-0.06953645431534781,\"enabled\":true}]},{\"inputId\":0,\"id\":30,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":7,\"outNode\":30,\"innovation\":318,\"weight\":0.8687722389409963,\"enabled\":true},{\"inNode\":1,\"outNode\":30,\"innovation\":718,\"weight\":-0.14117403833730013,\"enabled\":false},{\"inNode\":26,\"outNode\":30,\"innovation\":797,\"weight\":0.22862874892972,\"enabled\":true},{\"inNode\":9,\"outNode\":30,\"innovation\":852,\"weight\":0.9226222772900714,\"enabled\":false},{\"inNode\":16,\"outNode\":30,\"innovation\":913,\"weight\":0.22625932940525129,\"enabled\":false},{\"inNode\":82,\"outNode\":30,\"innovation\":1390,\"weight\":-0.5390624141361262,\"enabled\":true}]},{\"inputId\":0,\"id\":31,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":6,\"outNode\":31,\"innovation\":84,\"weight\":-0.1883591994895609,\"enabled\":false},{\"inNode\":24,\"outNode\":31,\"innovation\":332,\"weight\":0.47108943085971694,\"enabled\":true},{\"inNode\":4,\"outNode\":31,\"innovation\":798,\"weight\":-0.11920050919325373,\"enabled\":true},{\"inNode\":23,\"outNode\":31,\"innovation\":869,\"weight\":0.22707599462357844,\"enabled\":true},{\"inNode\":10,\"outNode\":31,\"innovation\":900,\"weight\":0.12302080223077728,\"enabled\":true},{\"inNode\":18,\"outNode\":31,\"innovation\":948,\"weight\":-0.29125641880405595,\"enabled\":true},{\"inNode\":11,\"outNode\":31,\"innovation\":1206,\"weight\":-0.3031364235413343,\"enabled\":true}]},{\"inputId\":0,\"id\":32,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":16,\"outNode\":32,\"innovation\":39,\"weight\":0.5800301199444714,\"enabled\":true},{\"inNode\":13,\"outNode\":32,\"innovation\":133,\"weight\":0.9084977637592617,\"enabled\":true},{\"inNode\":12,\"outNode\":32,\"innovation\":211,\"weight\":-0.07249273961228783,\"enabled\":true},{\"inNode\":18,\"outNode\":32,\"innovation\":741,\"weight\":0.4580140584879774,\"enabled\":false},{\"inNode\":53,\"outNode\":32,\"innovation\":1331,\"weight\":0.4580140584879774,\"enabled\":true}]},{\"inputId\":0,\"id\":33,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":17,\"outNode\":33,\"innovation\":43,\"weight\":-0.9142304787190836,\"enabled\":true},{\"inNode\":23,\"outNode\":33,\"innovation\":52,\"weight\":-0.2171879180182683,\"enabled\":true},{\"inNode\":16,\"outNode\":33,\"innovation\":110,\"weight\":0.8917240821373086,\"enabled\":true},{\"inNode\":5,\"outNode\":33,\"innovation\":135,\"weight\":-0.2716553535057826,\"enabled\":true},{\"inNode\":13,\"outNode\":33,\"innovation\":603,\"weight\":-0.5572915620510317,\"enabled\":true},{\"inNode\":26,\"outNode\":33,\"innovation\":784,\"weight\":0.31123674822523184,\"enabled\":false}]},{\"inputId\":0,\"id\":34,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":12,\"outNode\":34,\"innovation\":238,\"weight\":0.11848946953974301,\"enabled\":true},{\"inNode\":16,\"outNode\":34,\"innovation\":286,\"weight\":0.5229139716118507,\"enabled\":true},{\"inNode\":19,\"outNode\":34,\"innovation\":420,\"weight\":0.8107274060971141,\"enabled\":true},{\"inNode\":7,\"outNode\":34,\"innovation\":586,\"weight\":-0.24140525492764206,\"enabled\":false},{\"inNode\":24,\"outNode\":34,\"innovation\":724,\"weight\":-0.3135970407631646,\"enabled\":false},{\"inNode\":5,\"outNode\":34,\"innovation\":1077,\"weight\":-0.3685864612335483,\"enabled\":true},{\"inNode\":82,\"outNode\":34,\"innovation\":4676,\"weight\":0.19751804557230493,\"enabled\":true}]},{\"inputId\":0,\"id\":35,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":24,\"outNode\":35,\"innovation\":90,\"weight\":0.821957917955704,\"enabled\":false},{\"inNode\":7,\"outNode\":35,\"innovation\":106,\"weight\":0.20641759872511534,\"enabled\":false},{\"inNode\":21,\"outNode\":35,\"innovation\":177,\"weight\":0.12871856822478622,\"enabled\":false},{\"inNode\":22,\"outNode\":35,\"innovation\":731,\"weight\":0.8661500788161691,\"enabled\":true},{\"inNode\":17,\"outNode\":35,\"innovation\":905,\"weight\":-0.0451344895735981,\"enabled\":true},{\"inNode\":11,\"outNode\":35,\"innovation\":1001,\"weight\":-0.3195780887421421,\"enabled\":true},{\"inNode\":9,\"outNode\":35,\"innovation\":1083,\"weight\":-0.08059194259594815,\"enabled\":true},{\"inNode\":12,\"outNode\":35,\"innovation\":1240,\"weight\":-0.28393081269655873,\"enabled\":true},{\"inNode\":213,\"outNode\":35,\"innovation\":1685,\"weight\":0.32450766376254464,\"enabled\":true}]},{\"inputId\":0,\"id\":36,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":4,\"outNode\":36,\"innovation\":21,\"weight\":-0.5816353671171427,\"enabled\":false},{\"inNode\":24,\"outNode\":36,\"innovation\":205,\"weight\":0.028492618887527542,\"enabled\":true},{\"inNode\":9,\"outNode\":36,\"innovation\":261,\"weight\":0.5079141648142304,\"enabled\":false},{\"inNode\":21,\"outNode\":36,\"innovation\":505,\"weight\":0.19671328846049885,\"enabled\":true},{\"inNode\":25,\"outNode\":36,\"innovation\":655,\"weight\":0.9824116189226457,\"enabled\":true},{\"inNode\":5,\"outNode\":36,\"innovation\":670,\"weight\":-0.3684488268832826,\"enabled\":true},{\"inNode\":1,\"outNode\":36,\"innovation\":927,\"weight\":-0.41603598142729337,\"enabled\":false},{\"inNode\":14,\"outNode\":36,\"innovation\":952,\"weight\":0.07852510601019155,\"enabled\":true},{\"inNode\":13,\"outNode\":36,\"innovation\":956,\"weight\":-0.4170905707120633,\"enabled\":false},{\"inNode\":11,\"outNode\":36,\"innovation\":1039,\"weight\":-0.21503651237219756,\"enabled\":true},{\"inNode\":8,\"outNode\":36,\"innovation\":1174,\"weight\":0.7823412889762058,\"enabled\":false},{\"inNode\":169,\"outNode\":36,\"innovation\":1580,\"weight\":-0.4170905707120633,\"enabled\":true},{\"inNode\":82,\"outNode\":36,\"innovation\":3467,\"weight\":0.17068614794561682,\"enabled\":true}]},{\"inputId\":0,\"id\":169,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":13,\"outNode\":169,\"innovation\":1579,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":0,\"id\":53,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":18,\"outNode\":53,\"innovation\":1330,\"weight\":1.0,\"enabled\":true},{\"inNode\":15,\"outNode\":53,\"innovation\":10715,\"weight\":-0.9718794152214087,\"enabled\":true}]}]\n";
        GenomeEvaluator geno = GenomeSerializerDeserializer.getEvaluatorFromJson(json,
                NodeFunctionsCreator.linearUnit());
        fillUpEvaluationsAndPositions();
        double maxScore = 0;
        double currentScore = 0;
        for (TicTacToe ticTacToe : ticTacToeHashSet) {
            int[] board = ticTacToe.getBoard();
            int move = TicTacToeTrainer.getMoveIn27Out9(geno, board);
            TicTacToe ticTacToe1 = ticTacToe.makeMove(move);
            Integer evalBefore = ticTacToeHashMap.get(ticTacToe);
            maxScore += (double) (evalBefore + 1) / 10;
            //maxScore++;
            if (ticTacToe1 == null) {
                System.out.println(move);
                System.out.println(ticTacToe);
                continue;
            }
            Integer evalAfter = ticTacToeHashMap.get(ticTacToe1);
            currentScore += (double) (evalAfter + 1) / 10;
            //currentScore++;
            if (evalAfter == -1 && evalBefore != -1) {
                //System.out.println(ticTacToe);
                //System.out.println(ticTacToe1);
            }
        }
        System.out.println(maxScore);
        System.out.println(currentScore);

        GenomeSerializerDeserializer.drawNetworkToFile(json,
                new File("/Users/riccardo.frosini/IdeaProject/SmallMathExperiments/asd.jpg"));
    }

    @Test
    public void anotherTest() throws IOException {
        GenomeSerializerDeserializer.drawNetworkToFile("[{\"inputId\":0,\"id\":256,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":27,\"outNode\":256,\"innovation\":1785,\"weight\":0.2766710554239509,\"enabled\":false}]},{\"inputId\":0,\"id\":1,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":2945,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":228,\"outNode\":2945,\"innovation\":30819,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":1,\"id\":2,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":2,\"id\":3,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":3,\"id\":4,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":4,\"id\":5,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":5,\"id\":6,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":6,\"id\":7,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":7,\"id\":8,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":8,\"id\":9,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":9,\"id\":10,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":10,\"id\":11,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":11,\"id\":12,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":12,\"id\":13,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":13,\"id\":14,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":14,\"id\":15,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":15,\"id\":16,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":400,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":228,\"outNode\":400,\"innovation\":3856,\"weight\":0.9683475328830219,\"enabled\":false},{\"inNode\":1510,\"outNode\":400,\"innovation\":18017,\"weight\":0.9660579154808383,\"enabled\":true}]},{\"inputId\":16,\"id\":17,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":4369,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":1053,\"outNode\":4369,\"innovation\":42100,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":0,\"id\":401,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":23,\"outNode\":401,\"innovation\":3867,\"weight\":0.4245816330816026,\"enabled\":true},{\"inNode\":17,\"outNode\":401,\"innovation\":11597,\"weight\":0.7126682634938277,\"enabled\":true}]},{\"inputId\":17,\"id\":18,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":18,\"id\":19,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":1683,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":400,\"outNode\":1683,\"innovation\":19478,\"weight\":0.958733793757764,\"enabled\":true},{\"inNode\":1191,\"outNode\":1683,\"innovation\":34353,\"weight\":0.6089670834762111,\"enabled\":false},{\"inNode\":5467,\"outNode\":1683,\"innovation\":49872,\"weight\":0.6089670834762111,\"enabled\":true}]},{\"inputId\":19,\"id\":20,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":20,\"id\":21,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":21,\"id\":22,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":22,\"id\":23,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":23,\"id\":24,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":2200,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":158,\"outNode\":2200,\"innovation\":24504,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":24,\"id\":25,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":25,\"id\":26,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":26,\"id\":27,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":28,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":9,\"outNode\":28,\"innovation\":224,\"weight\":-0.8540932464855533,\"enabled\":false},{\"inNode\":5,\"outNode\":28,\"innovation\":841,\"weight\":0.896200793224028,\"enabled\":false},{\"inNode\":1,\"outNode\":28,\"innovation\":1090,\"weight\":0.9638981049869777,\"enabled\":false},{\"inNode\":6,\"outNode\":28,\"innovation\":1195,\"weight\":-0.70974856882263,\"enabled\":true},{\"inNode\":201,\"outNode\":28,\"innovation\":1643,\"weight\":0.380869141217234,\"enabled\":false},{\"inNode\":220,\"outNode\":28,\"innovation\":1687,\"weight\":0.9638981049869777,\"enabled\":true},{\"inNode\":228,\"outNode\":28,\"innovation\":1707,\"weight\":-0.8249281561555122,\"enabled\":false},{\"inNode\":237,\"outNode\":28,\"innovation\":2570,\"weight\":0.5807745329430065,\"enabled\":false},{\"inNode\":400,\"outNode\":28,\"innovation\":3857,\"weight\":0.44291471996760456,\"enabled\":false},{\"inNode\":401,\"outNode\":28,\"innovation\":16689,\"weight\":-0.6189000989494383,\"enabled\":true},{\"inNode\":1683,\"outNode\":28,\"innovation\":19479,\"weight\":-0.8949172954240036,\"enabled\":true}]},{\"inputId\":0,\"id\":156,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":23,\"outNode\":156,\"innovation\":1545,\"weight\":-0.7215069559407182,\"enabled\":false},{\"inNode\":401,\"outNode\":156,\"innovation\":3868,\"weight\":-0.7008799419959689,\"enabled\":false},{\"inNode\":1053,\"outNode\":156,\"innovation\":25210,\"weight\":-0.8812711129167069,\"enabled\":true}]},{\"inputId\":0,\"id\":29,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":3,\"outNode\":29,\"innovation\":329,\"weight\":-0.8804804540988745,\"enabled\":false},{\"inNode\":10,\"outNode\":29,\"innovation\":352,\"weight\":0.7535768731424274,\"enabled\":false},{\"inNode\":11,\"outNode\":29,\"innovation\":1141,\"weight\":-0.9782085246397298,\"enabled\":true}]},{\"inputId\":0,\"id\":1053,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":581,\"outNode\":1053,\"innovation\":13793,\"weight\":0.975144583448095,\"enabled\":true}]},{\"inputId\":0,\"id\":30,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":9,\"outNode\":30,\"innovation\":261,\"weight\":-0.6030018731562291,\"enabled\":false},{\"inNode\":18,\"outNode\":30,\"innovation\":441,\"weight\":-0.6151302408362698,\"enabled\":false},{\"inNode\":2,\"outNode\":30,\"innovation\":619,\"weight\":-0.6749612202902899,\"enabled\":false},{\"inNode\":17,\"outNode\":30,\"innovation\":1080,\"weight\":-0.907591402209008,\"enabled\":false},{\"inNode\":25,\"outNode\":30,\"innovation\":1193,\"weight\":-0.9876487264722142,\"enabled\":false},{\"inNode\":158,\"outNode\":30,\"innovation\":1550,\"weight\":-0.8677398446683631,\"enabled\":false},{\"inNode\":581,\"outNode\":30,\"innovation\":7234,\"weight\":0.6917030988607412,\"enabled\":false},{\"inNode\":1053,\"outNode\":30,\"innovation\":13794,\"weight\":0.7658075193652756,\"enabled\":true}]},{\"inputId\":0,\"id\":158,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":2,\"outNode\":158,\"innovation\":1549,\"weight\":-0.061140538124454136,\"enabled\":false},{\"inNode\":3,\"outNode\":158,\"innovation\":2938,\"weight\":-0.06627891335805956,\"enabled\":true},{\"inNode\":444,\"outNode\":158,\"innovation\":4599,\"weight\":-0.021163219880814852,\"enabled\":true}]},{\"inputId\":0,\"id\":31,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":1,\"outNode\":31,\"innovation\":608,\"weight\":-0.00901897108993075,\"enabled\":false},{\"inNode\":23,\"outNode\":31,\"innovation\":985,\"weight\":-0.16774194902440567,\"enabled\":false},{\"inNode\":14,\"outNode\":31,\"innovation\":1209,\"weight\":-0.7714067322814298,\"enabled\":false},{\"inNode\":26,\"outNode\":31,\"innovation\":1246,\"weight\":0.034431921054090195,\"enabled\":false},{\"inNode\":156,\"outNode\":31,\"innovation\":1546,\"weight\":0.059838721100964457,\"enabled\":false},{\"inNode\":299,\"outNode\":31,\"innovation\":2180,\"weight\":0.15861562162141019,\"enabled\":false},{\"inNode\":158,\"outNode\":31,\"innovation\":8242,\"weight\":-0.5882454617890251,\"enabled\":false},{\"inNode\":880,\"outNode\":31,\"innovation\":11737,\"weight\":-0.16763923873202563,\"enabled\":false},{\"inNode\":1191,\"outNode\":31,\"innovation\":15067,\"weight\":-0.6448348619234171,\"enabled\":true},{\"inNode\":1765,\"outNode\":31,\"innovation\":20254,\"weight\":-0.16763923873202563,\"enabled\":false},{\"inNode\":2558,\"outNode\":31,\"innovation\":27436,\"weight\":-0.16763923873202563,\"enabled\":true}]},{\"inputId\":0,\"id\":32,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":13,\"outNode\":32,\"innovation\":216,\"weight\":-0.7170983523186247,\"enabled\":false},{\"inNode\":15,\"outNode\":32,\"innovation\":343,\"weight\":0.564655023889994,\"enabled\":false},{\"inNode\":10,\"outNode\":32,\"innovation\":490,\"weight\":0.2976011157353794,\"enabled\":false},{\"inNode\":6,\"outNode\":32,\"innovation\":740,\"weight\":-0.8648434866555125,\"enabled\":false},{\"inNode\":26,\"outNode\":32,\"innovation\":1155,\"weight\":-0.19406098665864294,\"enabled\":false},{\"inNode\":19,\"outNode\":32,\"innovation\":1191,\"weight\":0.12816597441927297,\"enabled\":false},{\"inNode\":202,\"outNode\":32,\"innovation\":1645,\"weight\":0.948404186939235,\"enabled\":true},{\"inNode\":156,\"outNode\":32,\"innovation\":8645,\"weight\":0.1534049894806498,\"enabled\":true},{\"inNode\":1053,\"outNode\":32,\"innovation\":22269,\"weight\":0.38685255389445616,\"enabled\":false},{\"inNode\":4369,\"outNode\":32,\"innovation\":42101,\"weight\":0.38685255389445616,\"enabled\":true},{\"inNode\":1517,\"outNode\":32,\"innovation\":71485,\"weight\":-0.19482108521217412,\"enabled\":true}]},{\"inputId\":0,\"id\":33,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":16,\"outNode\":33,\"innovation\":23,\"weight\":0.49672969125720784,\"enabled\":false},{\"inNode\":25,\"outNode\":33,\"innovation\":822,\"weight\":-0.3210772869470366,\"enabled\":false},{\"inNode\":10,\"outNode\":33,\"innovation\":997,\"weight\":-0.3794145124076698,\"enabled\":false}]},{\"inputId\":0,\"id\":34,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":7,\"outNode\":34,\"innovation\":474,\"weight\":0.548439015438051,\"enabled\":true},{\"inNode\":24,\"outNode\":34,\"innovation\":497,\"weight\":0.2732145533108172,\"enabled\":false},{\"inNode\":12,\"outNode\":34,\"innovation\":903,\"weight\":-0.9189439715579866,\"enabled\":false},{\"inNode\":19,\"outNode\":34,\"innovation\":1220,\"weight\":0.33855077697415886,\"enabled\":true}]},{\"inputId\":0,\"id\":35,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":6,\"outNode\":35,\"innovation\":673,\"weight\":0.2918613514602497,\"enabled\":false},{\"inNode\":10,\"outNode\":35,\"innovation\":726,\"weight\":-0.42478978331026107,\"enabled\":true},{\"inNode\":3,\"outNode\":35,\"innovation\":796,\"weight\":0.9905870590780654,\"enabled\":false},{\"inNode\":27,\"outNode\":35,\"innovation\":1286,\"weight\":0.481423469848777,\"enabled\":false},{\"inNode\":1,\"outNode\":35,\"innovation\":1291,\"weight\":0.4974460301450143,\"enabled\":false},{\"inNode\":237,\"outNode\":35,\"innovation\":1729,\"weight\":-0.019483201870226417,\"enabled\":false},{\"inNode\":256,\"outNode\":35,\"innovation\":1786,\"weight\":-0.12980231031246062,\"enabled\":false},{\"inNode\":158,\"outNode\":35,\"innovation\":8028,\"weight\":-0.5603050413249792,\"enabled\":false},{\"inNode\":1334,\"outNode\":35,\"innovation\":16507,\"weight\":-0.12980231031246062,\"enabled\":true},{\"inNode\":2200,\"outNode\":35,\"innovation\":24505,\"weight\":-0.5603050413249792,\"enabled\":true}]},{\"inputId\":0,\"id\":36,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":6,\"outNode\":36,\"innovation\":942,\"weight\":-0.43050507816381656,\"enabled\":false},{\"inNode\":25,\"outNode\":36,\"innovation\":1207,\"weight\":0.5939673422610563,\"enabled\":true}]},{\"inputId\":0,\"id\":1191,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":299,\"outNode\":1191,\"innovation\":15066,\"weight\":-0.8941162253023488,\"enabled\":true}]},{\"inputId\":0,\"id\":299,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":156,\"outNode\":299,\"innovation\":2179,\"weight\":0.4173271054413987,\"enabled\":true}]},{\"inputId\":0,\"id\":1334,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":256,\"outNode\":1334,\"innovation\":16506,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":0,\"id\":440,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":13,\"outNode\":440,\"innovation\":4545,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":0,\"id\":444,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":2,\"outNode\":444,\"innovation\":4598,\"weight\":0.9828868519420145,\"enabled\":true}]},{\"inputId\":0,\"id\":581,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":158,\"outNode\":581,\"innovation\":7233,\"weight\":0.18614630537837404,\"enabled\":true},{\"inNode\":7,\"outNode\":581,\"innovation\":10959,\"weight\":0.8014315532991005,\"enabled\":false},{\"inNode\":1534,\"outNode\":581,\"innovation\":18226,\"weight\":0.8014315532991005,\"enabled\":true}]},{\"inputId\":0,\"id\":328,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":9,\"outNode\":328,\"innovation\":2550,\"weight\":1.0,\"enabled\":true},{\"inNode\":20,\"outNode\":328,\"innovation\":20530,\"weight\":0.30310853858699804,\"enabled\":true}]},{\"inputId\":0,\"id\":201,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":5,\"outNode\":201,\"innovation\":1642,\"weight\":-0.0071745454083254195,\"enabled\":false},{\"inNode\":12,\"outNode\":201,\"innovation\":4406,\"weight\":0.31773959971505655,\"enabled\":true}]},{\"inputId\":0,\"id\":202,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":13,\"outNode\":202,\"innovation\":1644,\"weight\":0.8720638197617471,\"enabled\":false},{\"inNode\":440,\"outNode\":202,\"innovation\":4546,\"weight\":0.8720638197617471,\"enabled\":true}]},{\"inputId\":0,\"id\":2390,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":23,\"outNode\":2390,\"innovation\":26158,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":0,\"id\":5467,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":1191,\"outNode\":5467,\"innovation\":49871,\"weight\":1.0,\"enabled\":true},{\"inNode\":444,\"outNode\":5467,\"innovation\":75000,\"weight\":0.8574997299453748,\"enabled\":true}]},{\"inputId\":0,\"id\":220,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":1,\"outNode\":220,\"innovation\":1686,\"weight\":1.0,\"enabled\":false},{\"inNode\":379,\"outNode\":220,\"innovation\":3390,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":0,\"id\":349,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":6,\"outNode\":349,\"innovation\":2872,\"weight\":-0.6167678867423619,\"enabled\":false},{\"inNode\":8,\"outNode\":349,\"innovation\":7497,\"weight\":0.6894009290865949,\"enabled\":false},{\"inNode\":23,\"outNode\":349,\"innovation\":10028,\"weight\":-0.1963487041517531,\"enabled\":false},{\"inNode\":3,\"outNode\":349,\"innovation\":17389,\"weight\":1.0,\"enabled\":true},{\"inNode\":2390,\"outNode\":349,\"innovation\":26159,\"weight\":-0.1963487041517531,\"enabled\":true}]},{\"inputId\":0,\"id\":228,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":9,\"outNode\":228,\"innovation\":1706,\"weight\":0.9138632540555293,\"enabled\":false},{\"inNode\":328,\"outNode\":228,\"innovation\":2551,\"weight\":0.898222425500908,\"enabled\":false},{\"inNode\":1517,\"outNode\":228,\"innovation\":18058,\"weight\":0.898222425500908,\"enabled\":true}]},{\"inputId\":0,\"id\":1765,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":880,\"outNode\":1765,\"innovation\":20253,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":0,\"id\":1510,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":228,\"outNode\":1510,\"innovation\":18016,\"weight\":0.9694163987240414,\"enabled\":false},{\"inNode\":2945,\"outNode\":1510,\"innovation\":30820,\"weight\":0.9694163987240414,\"enabled\":true}]},{\"inputId\":0,\"id\":237,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":6,\"outNode\":237,\"innovation\":1728,\"weight\":0.19580493676519264,\"enabled\":true},{\"inNode\":349,\"outNode\":237,\"innovation\":2873,\"weight\":0.38891266957084053,\"enabled\":false},{\"inNode\":18,\"outNode\":237,\"innovation\":3644,\"weight\":-0.19327410685245788,\"enabled\":true}]},{\"inputId\":0,\"id\":1517,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":328,\"outNode\":1517,\"innovation\":18057,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":0,\"id\":880,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":158,\"outNode\":880,\"innovation\":11736,\"weight\":0.7984159468545239,\"enabled\":true}]},{\"inputId\":0,\"id\":379,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":1,\"outNode\":379,\"innovation\":3389,\"weight\":1.0,\"enabled\":true},{\"inNode\":581,\"outNode\":379,\"innovation\":50927,\"weight\":0.24621495382209324,\"enabled\":true},{\"inNode\":1765,\"outNode\":379,\"innovation\":71454,\"weight\":0.5380041017147528,\"enabled\":true}]},{\"inputId\":0,\"id\":1534,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":7,\"outNode\":1534,\"innovation\":18225,\"weight\":1.0,\"enabled\":true}]},{\"inputId\":0,\"id\":2558,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":1765,\"outNode\":2558,\"innovation\":27435,\"weight\":1.0,\"enabled\":true}]}]\n",
                new File("/Users/riccardo.frosini/IdeaProject/SmallMathExperiments/asd.jpg"));

    }
}
