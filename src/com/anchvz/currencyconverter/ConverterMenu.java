package com.anchvz.currencyconverter;

import com.anchvz.currencyconverter.servicio.ConsumoAPI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;

public class ConverterMenu {

    public static void main(String[] args) {
        while (true) {
            // Mostrar menú principal
            String[] opciones = {
                    "Conversor de Moneda"
            };

            String opcionSeleccionada = (String) JOptionPane.showInputDialog(null,
                    "Seleccione una opción de conversión:",
                    "Menu",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (opcionSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Gracias por usar el Conversor de Divisas.");
                break;
            } else {
                switch (opcionSeleccionada) {
                    case "Conversor de Moneda":
                        realizarConversion();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            }
        }
    }

    // Método para realizar la conversión de moneda
    private static void realizarConversion() {
        boolean continuar = true;

        while (continuar) {
            // Obtener el valor a convertir
            double valor = obtenerValor();

            if (valor != -1) {
                // Selección de la moneda a la cual convertir
                String[] opcionesConversion = {
                        "De Peso a Dólar",
                        "De Peso a Euros",
                        "De Peso a Libras Esterlinas",
                        "De Peso a Yen Japonés",
                        "De Peso a Won sul-coreano",
                        "De Dólar a Peso",
                        "De Euros a Peso",
                        "De Libras Esterlinas a Peso",
                        "De Yen Japonés a Peso",
                        "De Won sul-coreano a Peso"
                };

                String monedaSeleccionada = (String) JOptionPane.showInputDialog(null,
                        "Elije la moneda a la que deseas convertir tu dinero",
                        "Monedas",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opcionesConversion,
                        opcionesConversion[0]);

                if (monedaSeleccionada != null) {
                    // Realizar la conversión según la opción seleccionada
                    double resultado = 0;

                    // Llamar al método correspondiente según la opción seleccionada
                    switch (monedaSeleccionada) {
                        case "De Peso a Dólar":
                            resultado = conversion(valor, "MXN","USD");
                            break;
                        case "De Peso a Euros":
                            resultado = conversion(valor, "MXN","EUR");
                            break;
                        case "De Peso a Libras Esterlinas":
                            resultado = conversion(valor, "MXN","GBP");
                            break;
                        case "De Peso a Yen Japonés":
                            resultado = conversion(valor, "MXN","JPY");
                            break;
                        case "De Peso a Won sul-coreano":
                            resultado = conversion(valor, "MXN","KRW");
                            break;
                        case "De Dólar a Peso":
                            resultado = conversion(valor,"USD" ,"MXN");
                            break;
                        case "De Euros a Peso":
                            resultado = conversion(valor,"EUR" ,"MXN");
                            break;
                        case "De Libras Esterlinas a Peso":
                            resultado = conversion(valor,"GBP" ,"MXN");
                            break;
                        case "De Yen Japonés a Peso":
                            resultado = conversion(valor,"JPY" ,"MXN");
                            break;
                        case "De Won sul-coreano a Peso":
                            resultado = conversion(valor,"KRW" ,"MXN");
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Opción de conversión no válida.");
                            continuar = false; // Si la opción no es válida, no continuar
                            break;
                    }

                    if (continuar) {
                        // Mostrar el resultado de la conversión
                        JOptionPane.showMessageDialog(null, String.format("Resultado de la conversión: %.2f %s", resultado, monedaSeleccionada));

                        // Preguntar al usuario si desea continuar
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar usando el programa?",
                                "Continuar", JOptionPane.YES_NO_CANCEL_OPTION);

                        if (respuesta != JOptionPane.YES_OPTION) {
                            continuar = false; // Si no desea continuar, salir del bucle interno
                        }
                    }
                } else {
                    continuar = false; // Si monedaSeleccionada es null, salir del bucle interno
                }
            } else {
                continuar = false; // Si valor es -1, salir del bucle interno
            }
        }
    }

    // Método para obtener el valor a convertir
    private static double obtenerValor() {
        double valor = -1;
        while (true) {
            try {
                String valorStr = JOptionPane.showInputDialog("Ingrese la cantidad de dinero que deseas convertir:");
                if (valorStr == null) {
                    return -1; // Usuario canceló
                }
                valor = Double.parseDouble(valorStr);
                break; // Salir del bucle si se ingresó un número válido
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un valor numérico válido.");
            }
        }
        return valor;
    }

    // Métodos para realizar las conversiones específicas
//    private static double pesoToDolar(double valor, String moneda1, String moneda2) {
//        // Implementa la conversión de Peso a Dólar
//        // Puedes utilizar tasas de cambio reales o ficticias
//        System.out.println(moneda1);
//        System.out.println(moneda2);
//        System.out.println(valor);
//        return valor / 20; // Conversión de prueba
//    }


//Conversion
    private static double conversion(double valor, String moneda1, String moneda2) {

        String base = "https://v6.exchangerate-api.com/v6/";
        String apiKey= "8a6a15910c0e47e0c1f01c58/pair/";
        String apiURL = base + apiKey + moneda1 + "/" + moneda2;

        var consumoApi = new ConsumoAPI();
        // Obtener datos de la API
        String json = consumoApi.obtenerDatos(apiURL);

        // Parsear el JSON
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        // Obtener el valor de conversion_rate
        //double conversionRate = jsonObject.getAsJsonObject("conversion_rate").getAsDouble();
        double conversionRate = jsonObject.get("conversion_rate").getAsDouble();

        // Imprimir para verificar
        System.out.println("Conversion rate: " + conversionRate);

        // Realizar la conversión usando el conversionRate obtenido
        double valorConvertido = valor * conversionRate;
        return valorConvertido;

    }

}
