import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Pokemon {

	public static void main(String[] args) throws IOException {

		String inputMovement = null;

		if (args.length == 0) { // Ler input da consola se nao existir argumento
			Scanner in = new Scanner(System.in);
			System.out.println("Introduzir Movimentos:");
			inputMovement = in.nextLine();
			in.close();
		} else { // Ler input do ficheiro definido no argumento
			File file = new File(args[0]);

			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));

				inputMovement = br.readLine();
				br.close();
				System.out.println("Input do Ficheiro " + args[0]);

			} catch (FileNotFoundException e) {
				System.out.println("Não foi possivel aceder ao ficheiro " + args[0]);
				System.exit(1);

			} catch (IOException e) {
				System.out.println("Erro a ler do ficheiro " + args[0]);
				br.close();
				System.exit(1);
			}

		}

		int x = 0, y = 0, pokemons = 1; // Pokemon da casa inicial capturado.

		char[] movements = inputMovement.toCharArray();

		HashSet<Long> map = InitializeNewSet();

		for (char movement : movements) {

			switch (movement) {

			case 'N':
			case 'n':
				y++;
				if (newZone(x, y, map))
					pokemons++;

				break;
			case 'E':
			case 'e':
				x++;
				if (newZone(x, y, map))
					pokemons++;
				break;
			case 'S':
			case 's':
				y--;
				if (newZone(x, y, map))
					pokemons++;
				break;
			case 'W':
			case 'w':
				x--;
				if (newZone(x, y, map))
					pokemons++;
				break;
			}
		}

		System.out.println(pokemons);

	}

	private static boolean newZone(int x, int y, HashSet<Long> set) {

		long key = CantorPairing(x, y);

		if (set.contains(key)) // Se o set conter o resultado da fun��o de cantor � porque aquela casa ja foi visitada
			return false;

		set.add(key);
		return true;

	}

	/*
	 * Inicializar o hash set com a posição inicial ja visitada
	 */
	private static HashSet<Long> InitializeNewSet() {
		HashSet<Long> set = new HashSet<Long>();
		set.add(0L);
		return set;
	}

	/*
	 * Função de emparelhamento de Cantor
	 */

	private static long CantorPairing(int x, int y) {

		// Converter x,y para valores naturais. Cantor não funciona com valores negativos
		long k1 = ZtoN(x);
		long k2 = ZtoN(y);

		long result = 1L * (((k1 + k2) * (k1 + k2 + 1)) / 2) + k2;

		return result;
	}

	/*
	 * https://stackoverflow.com/questions/919612/mapping-two-integers-to-one-in-a-unique-and-deterministic-way
	 */
	private static int ZtoN(int x) {
		if (x >= 0)
			x *= 2;
		else
			x = (x * (-2)) - 1;

		return x;
	}

}