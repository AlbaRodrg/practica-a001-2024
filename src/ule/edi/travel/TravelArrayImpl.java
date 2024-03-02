package ule.edi.travel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ule.edi.model.*;

public class TravelArrayImpl implements Travel {
	
	private static final Double DEFAULT_PRICE = 100.0;
	private static final Byte DEFAULT_DISCOUNT = 25;
	private static final Byte CHILDREN_EXMAX_AGE = 18;
	private Date travelDate;
	private int nSeats;
	
	private Double price;    // precio de entradas 
	private Byte discountAdvanceSale;   // descuento en venta anticipada (0..100)

	private Seat[] seats;
		
	public TravelArrayImpl(Date date, int nSeats) {
		// TODO
		this.travelDate = date;
		this.nSeats = nSeats;
		this.price = DEFAULT_PRICE;
		this.discountAdvanceSale = DEFAULT_DISCOUNT;
		this.seats = new Seat[this.nSeats];
		// utiliza los precios por defecto: DEFAULT_PRICE y DEFAULT_DISCOUNT definidos
		// en esta clase
		// debe crear el array de asientos

	}

	public TravelArrayImpl(Date date, int nSeats, Double price, Byte discount) {
		// TODO
		// Debe crear el array de asiento
		this.travelDate = date;
		this.nSeats = nSeats;
		this.price = price;
		this.discountAdvanceSale = discount;
		this.seats = new Seat[this.nSeats];

	}

	@Override
	public Byte getDiscountAdvanceSale() {
		// TODO Auto-generated method stub
		return this.discountAdvanceSale;
	}

	@Override
	public int getNumberOfSoldSeats() {
		// TODO Auto-generated method stub
		Byte count = 0;
		for (int i = 0; i < this.seats.length; i++) {

			if (this.seats[i] != null) {
				count++;
			}

		}
		return count;
	}

	@Override
	public int getNumberOfNormalSaleSeats() {
		// TODO Auto-generated method stub
		Byte count = 0;
		for (int i = 0; i < this.seats.length; i++) {
			if(getSeat(i) != null){
				if (getSeat(i).getAdvanceSale() == false) {
				count++;
				}
			}
		}
		return count;
	}

	@Override
	public int getNumberOfAdvanceSaleSeats() {
		// TODO Auto-generated method stub
		Byte count = 0;
		for (int i = 0; i < this.seats.length; i++) {

			if(getSeat(i) != null){

				if (getSeat(i).getAdvanceSale() == true) {

					count++;
				}
			}
			
		}
		return count;
	}

	@Override
	public int getNumberOfSeats() {
		// TODO Auto-generated method stub
		return this.nSeats;
	}

	@Override
	public int getNumberOfAvailableSeats() {
		// TODO Auto-generated method stub
		int count = 0;

		for (int i = 0; i < this.seats.length; i++) {

			if (this.seats[i] == null) {
				count++;
			}

		}
		return count;

	}

	@Override
	public Seat getSeat(int pos) {
		// TODO Auto-generated method stub
		Seat aux = null;
		if ((pos <= nSeats) && (pos > 0)) {
			aux = this.seats[pos - 1];
		} else {
			System.out.println("La posición introducida no es válida");
		}
		return aux;
	}

	@Override
	public Person refundSeat(int pos) {
		// TODO Auto-generated method stub

		Person holder = null;
		if ((pos <= nSeats) && (pos != 0)){
			if (this.seats[pos - 1] == null) {
				return holder;
			} else {
				holder = this.seats[pos -1].getHolder();
				this.seats[pos - 1] = null;
			}
		} else {
			System.out.println("La posición introducida no es válida");
		}

		return holder;
	}

	private boolean isChildren(int age) {
		// TODO Auto-generated method stub
		boolean result;

		if (age < CHILDREN_EXMAX_AGE) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	private boolean isAdult(int age) {
		// TODO Auto-generated method stub
		boolean result;

		if (age >= CHILDREN_EXMAX_AGE) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public List<Integer> getAvailableSeatsList() {
		// TODO Auto-generated method stub
		List<Integer> lista = new ArrayList<Integer>(nSeats);

		for(int i = 1; i <= getNumberOfSeats(); i++){
			if(getSeat(i) == null){
				lista.add(i);
			}
		}
		return lista;
	}

	@Override
	public List<Integer> getAdvanceSaleSeatsList() {
		// TODO Auto-generated method stub
		List<Integer> lista = new ArrayList<Integer>(nSeats);

		for(int i = 1; i <= getNumberOfSeats(); i++){

			if(getSeat(i) != null){ 

				if(isAdvanceSale(getSeat(i).getHolder())){
				lista.add(i);
				}
			}

		}
		return lista;
	}

	@Override
	public int getMaxNumberConsecutiveSeats() {
		// TODO Auto-generated method stub
		int aux=0, count= 0;
		for(int i = 0; i < this.seats.length; i++) {
			if(this.seats[i] == null) {
				count++;
			}else {
				count = 0;
			}
			if( count != 0) {
				aux = count;
			}
		}
		return aux;
	}

	@Override
	public boolean isAdvanceSale(Person p) {
		// TODO Auto-generated method stub
		boolean result= false;
		for (int i = 0; i < this.seats.length; i++) {
			if(getSeat(i) != null){
				if (p.getNif().equals(getSeat(i).getHolder().getNif())) {
					result = getSeat(i).getAdvanceSale();
				}
			}
		}
		return result;
	}

	@Override
	public Date getTravelDate() {
		// TODO Auto-generated method stub
		return this.travelDate;
	}

	@Override
	public boolean sellSeatPos(int pos, String nif, String name, int edad, boolean isAdvanceSale) {
		// TODO Auto-generated method stub
		boolean venta;
		if ((pos <= nSeats) && (pos > 0)){
			if (this.seats[pos - 1] != null) {
				venta = false;
			} else {
				Person usuario = new Person(nif, name, edad);
				this.seats[pos - 1] = new Seat(isAdvanceSale, usuario);
				venta = true;
			}
		} else {
			System.out.println("La posición introducida no es válida");
			venta = false;
		}
		return venta;
	}

	@Override
	public int getNumberOfChildren() {
		// TODO Auto-generated method stub

		byte count = 0;

		for (int i = 0; i < this.seats.length; i++) {
			if(this.seats[i] != null) {
				if (isChildren(this.seats[i].getHolder().getAge()) == true) {
					count++;
				}
			}
		}

		return count;
	}

	@Override
	public int getNumberOfAdults() {
		// TODO Auto-generated method stub

		byte count = 0;

		for (int i = 0; i < this.seats.length; i++) {
			if(this.seats[i] != null) {
				if (isAdult(this.seats[i].getHolder().getAge()) == true) {
					count++;
				}
			}
		}
		return count;
	}


	@Override
	public Double getCollectionTravel() {
		// TODO Auto-generated method stub
		Double total = 0.0;
		for (int i = 0; i < this.seats.length; i++) {
			if (getSeat(i) == null) {
				total = total + 0.0;
			} else {
				total = total + getSeatPrice(getSeat(i));
			}
		}
		return total;
	}

	@Override
	public int getPosPerson(String nif) {
		// TODO Auto-generated method stub
		int asiento = 0;
		for (int i = 0; i < this.seats.length; i++) {
			if(getSeat(i) != null){
				if (getSeat(i).getHolder().getNif().equals(nif)) {
					asiento = i ;//+ 1 pero daba error en los test;
				}
			}else{
				asiento = -1;
			}
		}
		return asiento;
	}

	@Override
	public int sellSeatFrontPos(String nif, String name, int edad, boolean isAdvanceSale) {
		// TODO Auto-generated method stub
		int posAux = -1;
		int check;
		for (int i = this.seats.length - 1; i >= 0; i--) {
			if (this.seats[i] == null) {
				posAux = i;
			}
		}
		if (posAux == -1) { // si la variable es igual a cero, significa que nunca se ha dado la condición
							// para entrar en el if del primer bucle for, y por lo tanto ha tenido que tomar
							// el valor por defecto
			check = -1;
		} else {
			Person usuario = new Person(nif, name, edad);
			Seat vendido = new Seat(isAdvanceSale, usuario);
			this.seats[posAux] = vendido;
			check = posAux + 1;
		}

		return check;
	}

	@Override
	public int sellSeatRearPos(String nif, String name, int edad, boolean isAdvanceSale) {
		// TODO Auto-generated method stub
		int posAux = -1;
		int check;
		for (int i = 0; i < this.seats.length; i++) {
			if (this.seats[i] == null) {
				posAux = i;
			}
		}
		if (posAux == -1) { // si la variable es igual a cero, significa que nunca se ha dado la condición
							// para entrar en el if del primer bucle for, y por lo tanto ha tenido que tomar
							// el valor por defecto
			check = -1;
		} else {
			Person usuario = new Person(nif, name, edad);
			Seat vendido = new Seat(isAdvanceSale, usuario);
			this.seats[posAux] = vendido;
			check = posAux + 1;
		}

		return check;
	}

	@Override
	public Double getSeatPrice(Seat seat) {
		// TODO Auto-generated method stub
		Double seatPrice = 0.0;
		if (seat.getAdvanceSale() == true) {
			seatPrice = this.price - (this.price * this.discountAdvanceSale / 100); //
		} else {
			seatPrice = this.price;
		}

		return seatPrice;
	}

	@Override
   public double getPrice() {
	   return this.price;
   }
}
