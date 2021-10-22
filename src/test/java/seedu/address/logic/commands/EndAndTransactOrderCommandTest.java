package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Inventory;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.order.Order;
import seedu.address.model.order.TransactionRecord;

public class EndAndTransactOrderCommandTest {

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_normalTransaction_itemRemoved() throws CommandException {
        ModelStubWithOrderAndInventory modelStub = new ModelStubWithOrderAndInventory();

        Item item = new Item(new Name("milk"), 120123, 10, new HashSet<>(), 1.1, 2.2);
        Inventory inventory = new Inventory();
        Order order = new Order();
        inventory.addItem(item);
        order.addItem(item);

        modelStub.setInventory(inventory);
        modelStub.setOrder(order);

        EndAndTransactOrderCommand command = new EndAndTransactOrderCommand();
        command.execute(modelStub);

        Inventory expectedInventory = new Inventory();

        assertEquals(modelStub.inventory, expectedInventory);
    }

    @Test
    public void execute_insufficientTransaction_itemRemoved() throws CommandException {
        // Order item has more quantity than item in inventory, inventory item should be removed
        ModelStubWithOrderAndInventory modelStub = new ModelStubWithOrderAndInventory();

        Item inventoryItem = new Item(new Name("milk"), 120123, 10, new HashSet<>(), 1.1, 2.2);
        Item orderItem = new Item(new Name("milk"), 120123, 15, new HashSet<>(), 1.1, 2.2);
        Inventory inventory = new Inventory();
        Order order = new Order();
        inventory.addItem(inventoryItem);
        order.addItem(orderItem);

        modelStub.setInventory(inventory);
        modelStub.setOrder(order);

        EndAndTransactOrderCommand command = new EndAndTransactOrderCommand();
        command.execute(modelStub);

        Inventory expectedInventory = new Inventory();

        assertEquals(modelStub.inventory, expectedInventory);
    }

    @Test
    public void execute_moreQuantityInventory_itemCountDecreased() throws CommandException {
        // Order item has more quantity than item in inventory, inventory item should be removed
        ModelStubWithOrderAndInventory modelStub = new ModelStubWithOrderAndInventory();

        Item inventoryItem = new Item(new Name("milk"), 120123, 15, new HashSet<>(), 1.1, 2.2);
        Item orderItem = new Item(new Name("milk"), 120123, 10, new HashSet<>(), 1.1, 2.2);
        Inventory inventory = new Inventory();
        Order order = new Order();
        inventory.addItem(inventoryItem);
        order.addItem(orderItem);

        modelStub.setInventory(inventory);
        modelStub.setOrder(order);

        EndAndTransactOrderCommand command = new EndAndTransactOrderCommand();
        command.execute(modelStub);

        Item transactedItem = new Item(new Name("milk"), 120123, 5, new HashSet<>(), 1.1, 2.2);
        Inventory expectedInventory = new Inventory();
        expectedInventory.addItem(transactedItem);

        assertEquals(modelStub.inventory, expectedInventory);
    }

    /**
     * A model stub that has only order related functionality.
     */
    private class ModelStubWithOrderAndInventory extends ModelStub {
        private Optional<Order> optionalOrder;
        private Inventory inventory;

        ModelStubWithOrderAndInventory() {
            optionalOrder = Optional.empty();
            inventory = new Inventory();
        }

        @Override
        public void setInventory(ReadOnlyInventory newData) {
            inventory.resetData(newData);
        }

        @Override
        public void setOrder(Order order) {
            EndAndTransactOrderCommandTest.ModelStubWithOrderAndInventory.this.optionalOrder = Optional.of(order);
        }

        @Override
        public boolean hasUnclosedOrder() {
            return optionalOrder.isPresent();
        }

        @Override
        public void addToOrder(Item item) {
            assert hasUnclosedOrder();
            optionalOrder.get().addItem(item);
        }

        @Override
        public void removeFromOrder(Item item, int amount) {
            assert hasUnclosedOrder();
            optionalOrder.get().removeItem(item, amount);
        }

        @Override
        public void transactAndClearOrder() {
            assert hasUnclosedOrder();
            TransactionRecord transaction = inventory.transactOrder(optionalOrder.get());
            // Reset to no order status
            optionalOrder = Optional.empty();
        }
    }
}
