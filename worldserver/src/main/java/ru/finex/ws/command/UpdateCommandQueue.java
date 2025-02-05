package ru.finex.ws.command;

import ru.finex.core.command.AbstractGameObjectCommand;
import ru.finex.core.command.CommandQueue;
import ru.finex.core.tick.TickStage;
import ru.finex.ws.command.impl.update.UpdateCommandContext;

/**
 * Executes a once-commands at
 *  {@link TickStage#PRE_UPDATE PRE_UPDATE},
 *  {@link TickStage#UPDATE UPDATE}
 *  {@link TickStage#POST_UPDATE POST_UPDATE}
 *  stages.
 *
 * @author m0nster.mind
 */
public interface UpdateCommandQueue extends CommandQueue<AbstractGameObjectCommand, UpdateCommandContext> {
}
