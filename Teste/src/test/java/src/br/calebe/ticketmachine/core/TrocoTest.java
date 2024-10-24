package src.br.calebe.ticketmachine.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrocoTest {

    @Test
    void testTrocoComNotasCorretas() {
        Troco troco = new Troco(7);
        assertEquals(1, troco.papeisMoeda[1].getQuantidade(), "Quantidade de notas de 5 está incorreta");
        assertEquals(1, troco.papeisMoeda[0].getQuantidade(), "Quantidade de notas de 2 está incorreta");
    }

    @Test
    void testHasNext() {
        Troco troco = new Troco(100);
        Troco.TrocoIterator iterator = troco.new TrocoIterator(troco);
        assertTrue(iterator.hasNext(), "Deve haver notas disponíveis");

        iterator.next();
        assertFalse(iterator.hasNext(), "Não deve haver mais notas");
    }

    @Test
    void testNextNaoAlteraPapeisMoeda() {
        Troco troco = new Troco(12);
        Troco.TrocoIterator iterator = troco.new TrocoIterator(troco);

        PapelMoeda papelMoeda = iterator.next();
        assertNotNull(papelMoeda, "Primeira chamada de next() deve retornar um valor válido");
        assertEquals(10, papelMoeda.getValor(), "O valor da nota retornada deve ser 10");

        papelMoeda = iterator.next();
        assertEquals(2, papelMoeda.getValor(), "O valor da nota retornada deve ser 2");
    }

    @Test
    void testRemoveWithoutNext() {
        Troco troco = new Troco(10);
        Troco.TrocoIterator iterator = troco.new TrocoIterator(troco);

        assertThrows(UnsupportedOperationException.class, iterator::remove, "Remove não deve ser suportado");
    }

    @Test
    void testRemoveNotSupported() {
        Troco troco = new Troco(100);
        Troco.TrocoIterator iterator = troco.new TrocoIterator(troco);
        assertThrows(UnsupportedOperationException.class, () -> iterator.remove(), "Remove não deve ser suportado");
    }

    @Test
    void testNextWithHasNextCheck() {
        Troco troco = new Troco(200);
        Troco.TrocoIterator iterator = troco.new TrocoIterator(troco);

        assertTrue(iterator.hasNext(), "Deve haver notas de 100");
        PapelMoeda pm = iterator.next();
        assertEquals(100, pm.getValor(), "Valor da nota deve ser 100");

        assertTrue(iterator.hasNext(), "Deve haver mais uma nota de 100");
        pm = iterator.next();
        assertEquals(100, pm.getValor(), "Valor da segunda nota deve ser 100");

        assertFalse(iterator.hasNext(), "Não deve haver mais notas");
    }
}