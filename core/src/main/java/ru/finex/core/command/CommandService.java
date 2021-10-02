package ru.finex.core.command;

/**
 * @author m0nster.mind
 */
public interface CommandService<T extends Command> {

    void executeCommands();
    void executeCommands(int limit);

    void offerPreCommand(T command);
    void offerCommand(T command);
    void offerPostCommand(T command);

}
