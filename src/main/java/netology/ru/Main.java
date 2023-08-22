package netology.ru;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger count3 = new AtomicInteger(0);
    public static AtomicInteger count4 = new AtomicInteger(0);
    public static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    if (text.length() == 3) {
                        count3.getAndAdd(1);
                    } else if (text.length() == 4) {
                        count4.getAndAdd(1);
                    } else {
                        count5.getAndAdd(1);
                    }
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isOneChar(text)) {
                    if (text.length() == 3) {
                        count3.getAndAdd(1);
                    } else if (text.length() == 4) {
                        count4.getAndAdd(1);
                    } else {
                        count5.getAndAdd(1);
                    }
                }
            }
        });
        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isMagic(text)) {
                    if (text.length() == 3) {
                        count3.getAndAdd(1);
                    } else if (text.length() == 4) {
                        count4.getAndAdd(1);
                    } else {
                        count5.getAndAdd(1);
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("Красивых слов с длиной 3: " + count3.toString() + "шт.");
        System.out.println("Красивых слов с длиной 4: " + count4.toString() + "шт.");
        System.out.println("Красивых слов с длиной 5: " + count5.toString() + "шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    static boolean isMagic(String word) {
        int length = word.length();
        int count = 0;
        for (int i = 0; i < length; i++) {
            if (i + 1 < length) {
                // Проверяем, является ли текущий символ меньшим или равным следующему
                if (word.charAt(i) <= word.charAt(i + 1)) {
                    count++;
                }
            }
        }
        if (count == length - 1) {
            // Проверяем, не являются ли все символы слова одинаковыми
            return !isOneChar(word);
        }
        return false;
    }

    private static boolean isOneChar(String word) {
        int length = word.length();
        char first = word.charAt(0);
        int count = 0;
        for (int i = 1; i < length; i++) { //первую букву не проверяем, так как она исходная
            if (first == word.charAt(i)) {
                count++;
            }
        }
        // Добавляем к счетчику 1, т.к. первую букву мы не проверяли
        return count + 1 == word.length();
    }

    static boolean isPalindrome(String word) {
        int length = word.length();
        // Двигаемся с обоих концов слова к середине
        for (int i = 0; i < (length / 2); i++) {
            // Сравниваем символы попарно
            if (word.charAt(i) != word.charAt(length - i - 1)) {
                // Если найдено несоответствие - слово не палиндром
                return false;
            }
        }
        return true;
    }
}