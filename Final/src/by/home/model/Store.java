package by.home.model;

import by.home.exceptions.EqualsItemIdException;
import by.home.exceptions.ItemNotFoundException;
import by.home.service.OperationWithItem;
import by.home.service.ItemComparator;

import java.util.LinkedList;
import java.util.List;

public class Store {
    private final LinkedList<Item> itemList;

    public Store() {
        itemList = new LinkedList<>();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<Item> getItemListByPrice() {
        LinkedList<Item> sortedItems = (LinkedList) itemList.clone();
        sortedItems.sort(new ItemComparator());
        return sortedItems;
    }

    public void addItem(Item item) throws ItemNotFoundException, EqualsItemIdException {
        boolean added = false;
        if (!itemList.contains(item)) {
            itemList.add(0, item);
            added = true;
        }
        printItemState(OperationWithItem.ADD, added, item.getName());
    }

    public void deleteItem(int id) throws ItemNotFoundException, EqualsItemIdException {
        boolean deleted = false;
        for (Item item : itemList) {
            if (item.getId() == id) {
                itemList.remove(item);
                deleted = true;
                break;
            }
        }
        printItemState(OperationWithItem.DELETE, deleted, String.valueOf(id));
    }

    public void editItem(Item item) throws EqualsItemIdException, ItemNotFoundException {
        boolean edited = false;
        if (itemList.contains(item)) {
            itemList.remove(item);
            itemList.add(item);
            edited = true;
        }
        printItemState(OperationWithItem.EDIT, edited, item.getName());
    }

    private void printItemState(OperationWithItem param, boolean state, String itemName) throws ItemNotFoundException, EqualsItemIdException {
        if (state) {
            switch (param) {
                case ADD -> System.out.printf("Item '%s' has been added to store\n", itemName);
                case EDIT -> System.out.printf("Item %s has been edited\n", itemName);
                case DELETE -> System.out.printf("Item with id = %s has been deleted\n", itemName);
            }
        } else {
            switch (param) {
                case ADD -> throw new EqualsItemIdException("Item with this id is already exists");
                case EDIT, DELETE -> throw new ItemNotFoundException("No item with this id");
            }
        }

    }
}
