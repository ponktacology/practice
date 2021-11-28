package country.pvp.practice.arena;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ArenaManager {

    private final Map<String, Arena> arenas = Maps.newHashMap();
    //  private final Map<Arena, Set<DuplicatedArena>> duplicatedArenas = Maps.newHashMap();

    public void add(@NotNull Arena arena) {
        arenas.put(arena.getName().toUpperCase(Locale.ROOT), arena);
    }

    public void remove(@NotNull Arena arena) {
        arenas.remove(arena.getName().toUpperCase(Locale.ROOT));
        //   duplicatedArenas.remove(arena);
    }

    public Arena get(@NotNull String name) {
        return arenas.get(name.toUpperCase(Locale.ROOT));
    }

    public void addAll(@NotNull Set<Arena> arenas) {
        arenas.forEach(this::add);
    }

   /* public void addDuplicatedArenas(Arena arena, Set<DuplicatedArena> arenas) {
        duplicatedArenas.put(arena, arenas);
    }

    public Set<DuplicatedArena> getDuplicatedArenas(Arena arena) {
        return duplicatedArenas.get(arena);
    } */

    public @NotNull Set<Arena> getAll() {
        return Collections.unmodifiableSet(arenas.values().stream().filter(Arena::isSetup).collect(Collectors.toSet()));
    }

    public Arena getRandom() {
        return getAll().toArray(new Arena[0])[(int) (arenas.size() * Math.random())];
    }

}
