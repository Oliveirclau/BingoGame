import java.util.*;

public class BingoGame {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<BingoCard> cards = new ArrayList<>();
    private static final Set<Integer> drawnNumbers = new HashSet<>();
    private static final Random rand = new Random();
    private static int roundCount = 0;

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Bingo Turma 1076!");
        System.out.println("Digite os nomes dos jogadores (separados por hífen):");
        String[] nomes = scanner.nextLine().split("-");

        for (String nome : nomes) {
            System.out.println("Criar cartela para " + nome + " (R para aleatória, M para manual):");
            String cardType = scanner.nextLine();
            BingoCard card = new BingoCard(nome, 5); // Supondo cartelas de 5 números

            if (cardType.equalsIgnoreCase("R")) {
                card.generateRandomCard();
            } else {
                System.out.println("Digite os números para a cartela (separados por vírgula):");
                String numbers = scanner.nextLine();
                card.generateManualCard(numbers);
            }

            cards.add(card);
        }

        while (true) {
            roundCount++;
            System.out.println("Rodada " + roundCount + ". Digite 'R' para sorteio aleatório, 'M' para manual ou 'X' para sair:");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("X")) {
                break;
            }

            List<Integer> numbersToDraw = new ArrayList<>();
            if (choice.equalsIgnoreCase("R")) {
                while (numbersToDraw.size() < 5) {
                    int number = rand.nextInt(60) + 1;
                    if (!drawnNumbers.contains(number)) {
                        numbersToDraw.add(number);
                        drawnNumbers.add(number);
                    }
                }
            } else {
                System.out.println("Digite cinco números para sortear (separados por vírgula):");
                String[] manualNumbers = scanner.nextLine().split(",");
                for (String numStr : manualNumbers) {
                    int number = Integer.parseInt(numStr.trim());
                    numbersToDraw.add(number);
                    drawnNumbers.add(number);
                }
            }

            System.out.println("Números sorteados: " + numbersToDraw);

            for (int number : numbersToDraw) {
                for (BingoCard card : cards) {
                    card.markNumber(number);
                    if (card.hasBingo()) {
                        endGame(card);
                        return;
                    }
                }
            }

            updateRanking();
        }
    }

    private static void updateRanking() {
        System.out.println("Ranking Atual:");
        cards.stream()
                .sorted((c1, c2) -> Integer.compare(c2.countHits(), c1.countHits()))
                .forEach(card -> System.out.println(card.getPlayerName() + ": " + card.countHits() + " acertos"));
    }

    private static void endGame(BingoCard winningCard) {
        System.out.println("Bingo! Vencedor: " + winningCard.getPlayerName());
        System.out.println("Quantidade de Rodadas: " + roundCount);
        System.out.println("Cartela Premiada: " + Arrays.toString(winningCard.getNumbers()));
        System.out.println("Números Sorteados: " + drawnNumbers);
    }
}



