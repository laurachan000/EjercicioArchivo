/*
Realizar el ejercicio del pdf 
Ing Sistemas computacionales 4°Semestre Grupo A
Por : Laura Maribel Chan Oliva
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.border.TitledBorder;


public class InterfazImpuestosCarro extends JFrame{

private CalculadorImpuestos Calculador;

private PanelVehiculo panelVehiculo;
private PanelDescuentos panelDescuentos;
private PanelResultados panelResultados;

public InterfazImpuestosCarro()throws Exception{
	Calculador = new CalculadorImpuestos();
    setTitle("Calculo impuestos");
    setSize(290,300);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    setLayout(new BorderLayout());

    panelVehiculo = new PanelVehiculo (null);
    add(panelVehiculo, BorderLayout.NORTH);

    panelDescuentos = new PanelDescuentos();
    add(panelDescuentos, BorderLayout.CENTER);

    panelResultados = new PanelResultados(null);
    add(panelResultados, BorderLayout.SOUTH);


}

    public class PanelVehiculo extends JPanel implements ActionListener{

    	private JTextField txtMarca;
		private JTextField txtLinea;
		private JTextField txtModelo;
		private JTextField txtValor;
		private JLabel labMarca;
		private JLabel labLinea;
		private JLabel labModelo;
		private JLabel labValor;
		
		private JLabel txtNada;
		private JButton butBuscar;
		public final static String BUSCAR = "buscar";
		
		private InterfazImpuestosCarro principal;
		
		public PanelVehiculo(InterfazImpuestosCarro v) {
			principal = v;
			
			setLayout(new GridLayout(5,2));
			setPreferredSize(new Dimension(0,130));
			TitledBorder border = BorderFactory.createTitledBorder("Datos del vehiculo");
			border.setTitleColor(Color.BLUE);
			setBorder(border);
			
			labMarca = new JLabel("Marca");
			labLinea = new JLabel("Linea");
			labModelo = new JLabel("Modelo");
			labValor = new JLabel("Valor");
			txtMarca = new JTextField();
			txtLinea = new JTextField();
			txtModelo = new JTextField();
			txtValor = new JTextField("$ 0");
			txtValor.setEditable(false);
			txtValor.setForeground(Color.BLUE);
			txtValor.setBackground(Color.WHITE);
			txtNada = new JLabel("");
			butBuscar = new JButton("Buscar");
			
			add(labMarca);
			add(txtMarca);
			add(labLinea);
			add(txtLinea);
			add(labModelo);
			add(txtModelo);
			add(labValor);
			add(txtValor);	
			add(txtNada);
			add(butBuscar);
			
			butBuscar.setActionCommand(BUSCAR);
			butBuscar.addActionListener(this);
			
			try {
				String strModelo = txtModelo.getText();
				int nModelo = Integer.parseInt(strModelo);
			}
			catch(Exception e) {
				txtModelo.setText("");
			}
		}
		
		public void refrescarPrecio(double precio) {
			DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();
			df.applyPattern("$ ###,###.##");
			txtValor.setText(df.format(precio));
		}
		
		public String darMarca() {
			return txtMarca.getText();
			}
		
		public String darLinea() {
			return txtLinea.getText();
		}
		
		public String darModelo() {
			return txtModelo.getText();
		}
		
		public void calcularPrecioVehiculo() {
			String unaMarca = panelVehiculo.darMarca();
			String unaLinea = panelVehiculo.darLinea();
			String unModelo = panelVehiculo.darModelo();
			
			double precio = Calculador.BuscarAvaluoVehiculo(unaMarca,unaLinea,unModelo);
			
			panelVehiculo.refrescarPrecio(precio);
		}
		
		public void calcularImpuestos() {
			String unaMarca = panelVehiculo.darMarca();
			String unaLinea = panelVehiculo.darLinea();
			String unModelo = panelVehiculo.darModelo();
			
			boolean descProntoPago = panelDescuentos.hayDescuentoProntoPago();
			boolean descServicioPublico = panelDescuentos.hayDescuentoServicioPublico();
			boolean descTrasladoCuenta = panelDescuentos.hayDescuentoTrasladoCuenta();
			
			double pago = Calculador.CalcularPago(unaMarca,unaLinea,unModelo,descProntoPago,descServicioPublico,descTrasladoCuenta);
			
			panelResultados.refrescarPago(pago);
		}

		public void actionPerformed(ActionEvent evento) {
			String comando = evento.getActionCommand();
			
			if(comando.equals(BUSCAR)) {
				JOptionPane.showMessageDialog(this, "Busqueda completada", "Calculo de Impuestos", JOptionPane.INFORMATION_MESSAGE);
			}	
		}
	}


    public class PanelDescuentos extends JPanel{
    	private JCheckBox cbPPago;
		private JCheckBox cbSPublico;
		private JCheckBox cbTCuenta;
		
		public PanelDescuentos() {
			setLayout(new GridLayout(2,2));
			TitledBorder border = BorderFactory.createTitledBorder("Descuentos");
			border.setTitleColor(Color.BLUE);
			setBorder(border);
			
			cbPPago = new JCheckBox("Pronto pago");
			cbSPublico = new JCheckBox("Servicio público");
			cbTCuenta = new JCheckBox("Traslado de cuenta");
			
			add(cbPPago);
			add(cbTCuenta);
			add(cbSPublico);
		}

		public boolean hayDescuentoTrasladoCuenta() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean hayDescuentoServicioPublico() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean hayDescuentoProntoPago() {
			// TODO Auto-generated method stub
			return false;
		}
    }

    public class PanelResultados extends JPanel implements ActionListener{

    	private JLabel labTotal;
		private JTextField txtTotal;
		private JButton butLimpiar;
		private JButton butCalcular;
		
		public final static String LIMPIAR = "limpiar";
		public final static String CALCULAR = "calcular";
		
		private InterfazImpuestosCarro principal;
		
		public PanelResultados(InterfazImpuestosCarro v) {
			principal = v;
			
			setLayout(new GridLayout(2,3));
			setPreferredSize(new Dimension(0,65));
			TitledBorder border = BorderFactory.createTitledBorder("Resultados");
			border.setTitleColor(Color.BLUE);
			setBorder(border);
			
			labTotal = new JLabel("Total a pagar");
			txtTotal = new JTextField("$ 0");
			butLimpiar = new JButton("Limpiar");
			butCalcular = new JButton("Calcular");
			
			txtTotal.setEditable(false);
			txtTotal.setForeground(Color.BLUE);
			txtTotal.setBackground(Color.WHITE);
			
			add(new JLabel(""));
			add(new JLabel(""));
			add(butLimpiar);
			add(labTotal);
			add(txtTotal);
			add(butCalcular);
			
			butLimpiar.setActionCommand(LIMPIAR);
			butCalcular.setActionCommand(CALCULAR);
			
			butLimpiar.addActionListener(this);
			butCalcular.addActionListener(this);
		}
		
		public void refrescarPago(double pago) {
			// TODO Auto-generated method stub
			
		}

		public void actionPerformed(ActionEvent evento) {
			String comando = evento.getActionCommand();
			
			if(comando.equals(LIMPIAR)) {
				JOptionPane.showMessageDialog(this, "Limpiado completado", "Calculo de Impuestos", JOptionPane.INFORMATION_MESSAGE);
			}
			else if(comando.equals(CALCULAR)) {
				JOptionPane.showMessageDialog(this, "Calculo completado", "Calculo de Impuestos", JOptionPane.INFORMATION_MESSAGE);
			}
		}
    }
    public static void main(String[] args) throws Exception {
		InterfazImpuestosCarro vent = new InterfazImpuestosCarro();
		
		vent.setVisible(true);
	}

}