package net.resolutemc.raidablebases.Hook;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.SideEffectSet;
import com.sk89q.worldedit.util.io.Closer;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class WorldEditHook {

    public static void save(Location primary, Location secondary, Location location, File schematicFile) {
        try (Closer closer = Closer.create()) {
            Region region = new CuboidRegion(BukkitAdapter.asBlockVector(primary), BukkitAdapter.asBlockVector(secondary));
            EditSession editSession = createEditSession(primary.getWorld());

            BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
            clipboard.setOrigin(BukkitAdapter.asBlockVector(location));

            ForwardExtentCopy copy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());

            Operations.complete(copy);

            FileOutputStream outputStream = closer.register(new FileOutputStream(schematicFile));
            ClipboardWriter writer = closer.register(BuiltInClipboardFormat.SPONGE_V3_SCHEMATIC.getWriter(outputStream));

            writer.write(clipboard);

        } catch (final Throwable t) {
            t.printStackTrace();
        }
    }

    public static void load(Location location, File schematicFile) {
        try (EditSession session = createEditSession(location.getWorld())) {
            ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
            ClipboardReader reader = format.getReader(new FileInputStream(schematicFile));

            Clipboard schematic = reader.read();

            Operation operation = new ClipboardHolder(schematic)
                    .createPaste(session)
                    .to(BukkitAdapter.asBlockVector(location))
                    .build();

            Operations.complete(operation);

        } catch (final Throwable t) {
            t.printStackTrace();
        }
    }

    private static EditSession createEditSession(World bukkitWorld) {
        final EditSession session = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(bukkitWorld));

        session.setSideEffectApplier(SideEffectSet.defaults());
        return session;
    }

}
