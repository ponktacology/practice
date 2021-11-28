package country.pvp.practice.ladder;

import country.pvp.practice.data.DataObject;
import country.pvp.practice.itembar.ItemBuilder;
import country.pvp.practice.kit.Kit;
import country.pvp.practice.serialization.ItemStackAdapter;
import lombok.Data;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Ladder implements DataObject {

    private final @NotNull String name;
    private String displayName;
    private ItemStack icon;
    private @NotNull Kit kit = new Kit();
    private ItemStack[] editorItems;
    private boolean ranked;

    @Override
    public @NotNull Document getDocument() {
        Document document = new Document("_id", getId());
        document.put("displayName", displayName);
        document.put("icon", ItemStackAdapter.toJson(icon));
        document.put("kit", kit.getDocument());
        document.put("ranked", ranked);
        document.put("editorItems", Arrays.stream(editorItems).map(ItemStackAdapter::toJson).collect(Collectors.toList()));
        return document;
    }


    @Override
    public void applyDocument(@NotNull Document document) {
        displayName = document.getString("displayName");
        icon = ItemStackAdapter.fromJson(document.getString("icon"));
        kit.applyDocument(document.get("kit", Document.class));
        ranked = document.getBoolean("ranked");
        editorItems = (ItemStack[]) document.get("editorItems", List.class).stream().map(it -> ItemStackAdapter.fromJson((String) it)).toArray(ItemStack[]::new);
    }

    @Override
    public @NotNull String getCollection() {
        return "ladders";
    }

    @Override
    public @NotNull String getId() {
        return name;
    }

    public boolean isSetup() {
        return displayName != null && icon != null;
    }

    public @Nullable ItemStack getIcon() {
        if (!isSetup()) return null;

        return new ItemBuilder(icon.clone()).name(displayName).build();
    }

    public void setInventory(ItemStack[] inventory) {
        kit.setInventory(inventory);
    }

    public void setArmor(ItemStack[] armor) {
        kit.setArmor(armor);
    }

    public ItemStack @NotNull [] getEditorItems() {
        return editorItems == null ? new ItemStack[0] : Arrays.stream(editorItems).map(it -> it == null ? new ItemStack(Material.AIR) : it.clone()).toArray(ItemStack[]::new);
    }

    public void setEditorItems(ItemStack[] items) {
        editorItems = items;
    }
}
