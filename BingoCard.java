import java.util.Random;

public class BingoCard {
    private final String playerName;
    private final int[] numbers;
    private final boolean[] hits;

    public BingoCard(String playerName, int cardSize) {
        this.playerName = playerName;
        this.numbers = new int[cardSize];
        this.hits = new boolean[cardSize];
    }

    public void generateRandomCard() {
        Random rand = new Random();
        for (int i = 0; i < numbers.length; i++) {
            int nextNumber;
            do {
                nextNumber = rand.nextInt(60) + 1;
            } while (isNumberPresent(nextNumber));
            numbers[i] = nextNumber;
        }
    }

    public void generateManualCard(String input) {
        String[] inputNumbers = input.split(",");
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(inputNumbers[i].trim());
        }
    }

    public void markNumber(int number) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == number) {
                hits[i] = true;
                break;
            }
        }
    }

    private boolean isNumberPresent(int number) {
        for (int num : numbers) {
            if (num == number) {
                return true;
            }
        }
        return false;
    }

    public boolean hasBingo() {
        for (boolean hit : hits) {
            if (!hit) return false;
        }
        return true;
    }

    public int countHits() {
        int hitCount = 0;
        for (boolean hit : hits) {
            if (hit) hitCount++;
        }
        return hitCount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public boolean[] getHits() {
        return hits;
    }
}
