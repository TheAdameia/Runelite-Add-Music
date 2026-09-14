package com.addmusic;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

@Slf4j
@PluginDescriptor(
	name = "AddMusic"
)
public class AddMusicPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private AddMusicConfig config;

    @Inject
    private ClientToolbar clientToolbar;

    private AddMusicPanel panel;
    private NavigationButton navigationButton;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Example started!");

        panel = new AddMusicPanel();

		BufferedImage icon = ImageIO.read(
				AddMusicPlugin.class.getResourceAsStream("/icon.png")
		);

        navigationButton = NavigationButton.builder()
                .tooltip("AddMusic")
				.icon(icon)
                .panel(panel)
                .build();

        clientToolbar.addNavigation(navigationButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
        clientToolbar.removeNavigation(navigationButton);

		log.debug("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
    AddMusicConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AddMusicConfig.class);
	}
}
