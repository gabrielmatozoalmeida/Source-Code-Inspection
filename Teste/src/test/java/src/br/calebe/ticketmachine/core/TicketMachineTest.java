package src.br.calebe.ticketmachine.core;

import br.calebe.ticketmachine.exception.PapelMoedaInvalidaException;
import br.calebe.ticketmachine.exception.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

class TicketMachineTest {

    TicketMachine ticketMachine;

    @BeforeEach
    void setUp() {
        ticketMachine = new TicketMachine(10);
    }

    @Test
    void testInserirQuantiaValida() throws PapelMoedaInvalidaException {
        ticketMachine.inserir(10);
        assertEquals(10, ticketMachine.getSaldo(), "O saldo deve ser 10 após inserir 10");
    }

    @Test
    void testInserirQuantiaInvalida() {
        assertThrows(PapelMoedaInvalidaException.class, () -> {
            ticketMachine.inserir(3);
        }, "Deveria lançar PapelMoedaInvalidaException para cédula inválida");
    }

    @Test
    void testImprimirComSaldoInsuficiente() {
        assertThrows(SaldoInsuficienteException.class, () -> {
            ticketMachine.imprimir();
        }, "Deveria lançar SaldoInsuficienteException com saldo insuficiente");
    }

    @Test
    void testImprimirComSaldoSuficiente() throws SaldoInsuficienteException, PapelMoedaInvalidaException {
        ticketMachine.inserir(20);
        String ticket = ticketMachine.imprimir();
        assertTrue(ticket.contains("R$ 10,00"), "O ticket deve conter o valor R$ 10,00");
        assertEquals(10, ticketMachine.getSaldo(), "O saldo remanescente deve ser 10");
    }

    @Test
    void testGetTroco() throws PapelMoedaInvalidaException, SaldoInsuficienteException {
        ticketMachine.inserir(50);
        ticketMachine.imprimir();
        Iterator<Integer> troco = ticketMachine.getTroco();
        assertTrue(troco.hasNext(), "Deve haver troco");
        assertEquals(20, troco.next(), "O troco deve começar com uma cédula de 20");
        assertEquals(20, troco.next(), "O troco deve conter mais uma cédula de 20");
    }

    @Test
    void testInserirParaDesempenho() throws PapelMoedaInvalidaException {
        ticketMachine.inserir(10);
        assertEquals(10, ticketMachine.getSaldo(), "O saldo deve ser 10 após inserir uma cédula válida");
    }

    @Test
    void testValidarSaldoNaoNegativo() throws PapelMoedaInvalidaException, SaldoInsuficienteException {
        ticketMachine.inserir(5);
        assertThrows(SaldoInsuficienteException.class, () -> {
            ticketMachine.imprimir();
        }, "Não deve permitir que o saldo fique negativo");
        assertTrue(ticketMachine.getSaldo() >= 0, "O saldo nunca deve ser negativo");
    }
}