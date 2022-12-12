import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.println("Введите максимальный размер кэша");
    int size = in.nextInt();
    LruCache<Integer, Integer> cache = new LruCache<>(size);
    System.out.println("Кэш создан.\n Команды:\n 0 - вывести кэш\n 1 a - получить значение по ключу a\n 2 a b - положить b по ключу a");

    while (in.hasNextLine()) {
      String line = in.nextLine();
      Scanner sc = new Scanner(line);
      if (sc.hasNext()) {
        switch (sc.nextInt()) {
          case 0:
            for (int element : cache.getList())
              System.out.print(element + " ");
            System.out.println();
            break;
          case 1:
            int key = sc.nextInt();
            try {
              var value = cache.get(key);
              if (value == null)
                System.out.println("Отсутствует в кэше");
              else
                System.out.println(value);
            } catch (NumberFormatException e) {
              System.out.println("Неправильные входные данные");
            }
            break;
          case 2:
            key = sc.nextInt();
            int value = sc.nextInt();
            try {
              Integer put = cache.put(key, value);
              if (put != null)
                 System.out.println("Элементов в кэше: " + put);
            } catch (NumberFormatException e) {
              System.out.println("Неправильные входные данные");
            }
        }
      }
    }
  }
}
