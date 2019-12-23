package ai.maths.neat;

import ai.maths.neat.neuralnetwork.GenomeSerializerDeserializer;
import ai.maths.neat.neuralnetwork.functions.GenomeEvaluator;
import ai.maths.neat.utils.NodeFunctionsCreator;
import ai.maths.tictactoe.TicTacToe;
import org.junit.Test;

import static ai.maths.neat.TicTacToeTrainer.*;

public class Test1 {

    @Test
    public void test() {
        GenomeEvaluator geno = GenomeSerializerDeserializer.getEvaluatorFromJson("[{\"inputId\":0,\"id\":1,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":1,\"id\":2,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":2,\"id\":3,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":3,\"id\":4,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":4,\"id\":5,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":5,\"id\":6,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":6,\"id\":7,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":7,\"id\":8,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":8,\"id\":9,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":9,\"id\":10,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":10,\"id\":11,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":11,\"id\":12,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":12,\"id\":13,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":13,\"id\":14,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":14,\"id\":15,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":15,\"id\":16,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":16,\"id\":17,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":17,\"id\":18,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":18,\"id\":19,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":19,\"id\":20,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":20,\"id\":21,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":21,\"id\":22,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":22,\"id\":23,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":23,\"id\":24,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":24,\"id\":25,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":25,\"id\":26,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":26,\"id\":27,\"type\":\"INPUT\",\"backConnections\":[]},{\"inputId\":0,\"id\":28,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":1,\"outNode\":28,\"innovation\":280001,\"weight\":0.8550945270402652,\"enabled\":true},{\"inNode\":9,\"outNode\":28,\"innovation\":280009,\"weight\":-0.5402592635528903,\"enabled\":true}]},{\"inputId\":0,\"id\":29,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":4,\"outNode\":29,\"innovation\":290004,\"weight\":0.24784485674874346,\"enabled\":true},{\"inNode\":10,\"outNode\":29,\"innovation\":290010,\"weight\":0.048838737069749394,\"enabled\":false},{\"inNode\":19,\"outNode\":29,\"innovation\":290019,\"weight\":0.20517463330577146,\"enabled\":false},{\"inNode\":22,\"outNode\":29,\"innovation\":290022,\"weight\":-0.6829145578983264,\"enabled\":false}]},{\"inputId\":0,\"id\":30,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":7,\"outNode\":30,\"innovation\":300007,\"weight\":0.9776838573448144,\"enabled\":true},{\"inNode\":14,\"outNode\":30,\"innovation\":300014,\"weight\":-0.1490973548781827,\"enabled\":true},{\"inNode\":20,\"outNode\":30,\"innovation\":300020,\"weight\":-0.21093617662266603,\"enabled\":true}]},{\"inputId\":0,\"id\":31,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":1,\"outNode\":31,\"innovation\":310001,\"weight\":0.2674030724252404,\"enabled\":false},{\"inNode\":9,\"outNode\":31,\"innovation\":310009,\"weight\":0.0941848384875076,\"enabled\":true},{\"inNode\":10,\"outNode\":31,\"innovation\":310010,\"weight\":0.27661364154871754,\"enabled\":true},{\"inNode\":11,\"outNode\":31,\"innovation\":310011,\"weight\":-0.6370465060791726,\"enabled\":true},{\"inNode\":12,\"outNode\":31,\"innovation\":310012,\"weight\":0.0278329720467083,\"enabled\":true},{\"inNode\":15,\"outNode\":31,\"innovation\":310015,\"weight\":-0.0592843793076281,\"enabled\":true},{\"inNode\":16,\"outNode\":31,\"innovation\":310016,\"weight\":-0.9369142426784548,\"enabled\":true}]},{\"inputId\":0,\"id\":32,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":13,\"outNode\":32,\"innovation\":320013,\"weight\":0.9924520504893807,\"enabled\":true},{\"inNode\":15,\"outNode\":32,\"innovation\":320015,\"weight\":0.11873205532011656,\"enabled\":false},{\"inNode\":24,\"outNode\":32,\"innovation\":320024,\"weight\":0.6680369787696588,\"enabled\":false},{\"inNode\":27,\"outNode\":32,\"innovation\":320027,\"weight\":0.17803156530542844,\"enabled\":true},{\"inNode\":110,\"outNode\":32,\"innovation\":320110,\"weight\":-0.9339553119979478,\"enabled\":true}]},{\"inputId\":0,\"id\":33,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":1,\"outNode\":33,\"innovation\":330001,\"weight\":0.7873376201199004,\"enabled\":false},{\"inNode\":9,\"outNode\":33,\"innovation\":330009,\"weight\":-0.2950982067765213,\"enabled\":true},{\"inNode\":15,\"outNode\":33,\"innovation\":330015,\"weight\":-0.6678911050471618,\"enabled\":false},{\"inNode\":16,\"outNode\":33,\"innovation\":330016,\"weight\":0.6025160284342321,\"enabled\":true},{\"inNode\":19,\"outNode\":33,\"innovation\":330019,\"weight\":-0.42971966126129785,\"enabled\":true},{\"inNode\":21,\"outNode\":33,\"innovation\":330021,\"weight\":0.9245044621032996,\"enabled\":false},{\"inNode\":22,\"outNode\":33,\"innovation\":330022,\"weight\":0.5600605005736125,\"enabled\":false},{\"inNode\":27,\"outNode\":33,\"innovation\":330027,\"weight\":0.024867865958111038,\"enabled\":false}]},{\"inputId\":0,\"id\":34,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":3,\"outNode\":34,\"innovation\":340003,\"weight\":-0.18827584905382852,\"enabled\":false},{\"inNode\":13,\"outNode\":34,\"innovation\":340013,\"weight\":0.7612698509033579,\"enabled\":true},{\"inNode\":19,\"outNode\":34,\"innovation\":340019,\"weight\":0.694378186153754,\"enabled\":true}]},{\"inputId\":0,\"id\":35,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":13,\"outNode\":35,\"innovation\":350013,\"weight\":0.8702854933743329,\"enabled\":true},{\"inNode\":16,\"outNode\":35,\"innovation\":350016,\"weight\":-0.9242599817006629,\"enabled\":true},{\"inNode\":21,\"outNode\":35,\"innovation\":350021,\"weight\":0.1855022534446436,\"enabled\":true},{\"inNode\":25,\"outNode\":35,\"innovation\":350025,\"weight\":-0.7587147533396481,\"enabled\":false}]},{\"inputId\":0,\"id\":36,\"type\":\"OUTPUT\",\"backConnections\":[{\"inNode\":8,\"outNode\":36,\"innovation\":360008,\"weight\":0.005659002044528558,\"enabled\":false},{\"inNode\":19,\"outNode\":36,\"innovation\":360019,\"weight\":-0.7772064530209413,\"enabled\":true},{\"inNode\":22,\"outNode\":36,\"innovation\":360022,\"weight\":0.9003688030062369,\"enabled\":false},{\"inNode\":25,\"outNode\":36,\"innovation\":360025,\"weight\":0.8650366533887955,\"enabled\":true}]},{\"inputId\":0,\"id\":110,\"type\":\"HIDDEN\",\"backConnections\":[{\"inNode\":13,\"outNode\":110,\"innovation\":1100013,\"weight\":-0.5530350384410719,\"enabled\":true},{\"inNode\":23,\"outNode\":110,\"innovation\":1100023,\"weight\":0.12224388678699433,\"enabled\":true}]}]\n",
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
    }
}
