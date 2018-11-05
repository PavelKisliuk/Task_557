import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Task_557 {
	public static void main(String[] args) {
		SquareMatrix test = new SquareMatrix();
		try(Formatter output = new Formatter("OUTPUT.TXT")) {
			output.format(test.toString());
		}catch (FileNotFoundException | FormatterClosedException e) {
			e.printStackTrace();
		}
	}

	//-----------------------------------------------------------------------------
	/*public*/ static class SquareMatrix {
		//-----------------------------------------------------------------------------fields
		private int quantityOfMatrixs;
		private int sizeOfMatrix;
		private int rowAnn;
		private int columnAnn;
		private int pureNumberP;
		private ArrayList<Integer> currentString;
		private int soughtElement;
		//-----------------------------------------------------------------------------
		/*public*/ SquareMatrix(final String path)
		{

			this.currentString = new ArrayList<>();
			try(final BufferedReader input = new BufferedReader (new FileReader(path))) {
				//-----------------------------------------------------------------------------
				if(input.ready()) {
					//-----------------------------------------------------------------------------
					String buffer = input.readLine(); //read data from file
					if(this.isCorrectQuantityAndSize(buffer)) {
						String[] tokensString = buffer.split(" ");
						int numberOfParameter = 0;
						this.quantityOfMatrixs = Integer.valueOf(tokensString[numberOfParameter++]);
						this.sizeOfMatrix = Integer.valueOf(tokensString[numberOfParameter]);
					}
					else {
						throw new IOException("Incorrect quantity and size!");
					}
					//-----------------------------------------------------------------------------
					buffer = input.readLine(); //read data from file
					if(this.isCorrectRowAndColumn(buffer)) {
						String[] tokensString = buffer.split(" ");
						int numberOfParameter = 0;
						this.rowAnn = Integer.valueOf(tokensString[numberOfParameter++]) - 1;
						this.columnAnn = Integer.valueOf(tokensString[numberOfParameter]) - 1;
					}
					else {
						throw new IOException("Incorrect Ann`s row and column!");
					}
					//-----------------------------------------------------------------------------
					buffer = input.readLine(); //read data from file
					if(this.isCorrectNumber(buffer)) {
						this.pureNumberP = Integer.valueOf(buffer);
					}
					else {
						throw new IOException("Incorrect pure number P!");
					}
					input.readLine();//read data from file
					//-----------------------------------------------------------------------------
					for(int matrixRow = 0; matrixRow < this.sizeOfMatrix; matrixRow++) {
						if(matrixRow == this.rowAnn) {
							buffer = input.readLine(); //read data from file
							if(this.isCorrectString(buffer)) {
								for(String s : buffer.split(" ")) {
									this.currentString.add(Integer.valueOf(s));
								}
							}
							else {
								throw new IOException("Incorrect value in matrix!");
							}
						}
						else {
							input.readLine();//read data from file
						}
					}
					//-----------------------------------------------------------------------------
					for(int currentMatrix = 1; currentMatrix < this.quantityOfMatrixs; currentMatrix++) {
						input.readLine(); //read data from file
						this.currentString = new Matrix(input, this.sizeOfMatrix, this.pureNumberP).multiple(this.currentString, this.pureNumberP);
					}
					this.soughtElement = this.currentString.get(this.columnAnn);
					//-----------------------------------------------------------------------------
				}
				//-----------------------------------------------------------------------------
				else {
					throw new IOException("File is empty!");
				}
				//-----------------------------------------------------------------------------
			}catch (IOException | NoSuchElementException e) {

				e.printStackTrace();
			}
		}

		/*public*/ SquareMatrix()
		{
			this("INPUT.TXT");
		}
		//-----------------------------------------------------------------------------methods for constructors
		private boolean isCorrectQuantityAndSize(final String s)
		{
			if(s.matches("[1-9]+\\d* [1-9]+\\d*")) {
				for(String token : s.split(" ")) {
					if((Integer.valueOf(token) >= 1) || (Integer.valueOf(token) <= 130)) {
						return true;
					}
				}
			}
			return false;
		}

		private boolean isCorrectRowAndColumn(final String s)
		{
			if(s.matches("[1-9]+\\d* [1-9]+\\d*")) {
				for(String token : s.split(" ")) {
					if((Integer.valueOf(token) >= 1) || (Integer.valueOf(token) <= this.sizeOfMatrix)) {
						return true;
					}
				}
			}
			return false;
		}

		private boolean isCorrectNumber(final String s)
		{
			return ((s.matches("[1-9]+\\d*")) && (Integer.valueOf(s) <= 1000));
		}

		private boolean isCorrectString(final String s)
		{
			for(String token : s.split(" ")) {
				if(token.matches("[1-9]+\\d*")) {
					if ((Integer.valueOf(token) <= this.pureNumberP)) {
						return true;
					}
				}
			}
			return false;
		}
		//-----------------------------------------------------------------------------
		//-----------------------------------------------------------------------------methods
		@Override
		public String toString()
		{
			return String.valueOf(this.soughtElement);
		}
	}
	//-----------------------------------------------------------------------------
	/*public*/ static class Matrix {
		private int matrix[][];
		private int size;
		//-----------------------------------------------------------------------------constructors
		/*public*/ Matrix(final BufferedReader input, final int size, final int module) {
			try {
				//-----------------------------------------------------------------------------
				this.size = size;
				this.matrix = new int[this.size][this.size];
				//-----------------------------------------------------------------------------
				for (int row = 0; row < this.size; row++) {
					//-----------------------------------------------------------------------------
					String buffer = input.readLine(); //read data from file
					if(this.isCorrectString(buffer, module)) {
						String[] tokensString = buffer.split(" ");
						for (int column = 0; column < this.size; column++) {
							this.matrix[row][column] = Integer.valueOf(tokensString[column]);
						}
					}
					//-----------------------------------------------------------------------------
					else {
						throw new IOException("Incorrect value in matrix!");
					}
					//-----------------------------------------------------------------------------
				}
			}catch (IOException | NoSuchElementException e) {
				e.printStackTrace();
			}
		}
		//-----------------------------------------------------------------------------methods for constructors
		private boolean isCorrectString(final String s, final int module)
		{
			for(String token : s.split(" ")) {
				if(token.matches("[1-9]+\\d*")) {
					if ((Integer.valueOf(token) <= module)) {
						return true;
					}
				}
			}
			return false;
		}
		//-----------------------------------------------------------------------------
		//-----------------------------------------------------------------------------methods
		private int getMatrixElement(final int row, final int column)
		{
			return this.matrix[row][column];
		}

		/*public*/ ArrayList<Integer> multiple(final ArrayList<Integer> currentString, final int module)
		{
			int[] newArray = new int[this.size];
			int sum = 0;
			//-----------------------------------------------------------------------------
			for (int row = 0; row < this.size; row++) {
				for (int column = 0; column < this.size; column++) {
					sum = this.module(sum +
							this.module(currentString.get(column) * this.getMatrixElement(column, row), module), module);
				}
				newArray[row] = sum;
				sum = 0;
			}
			//-----------------------------------------------------------------------------
			ArrayList<Integer> newString = new ArrayList<>();
			for(int n : newArray) {
				newString.add(n);
			}
			return newString;
		}

		private int module(final int element, final int module)
		{
			return (element >= module) ? ((element) % module) : (element);
		}
	}
}