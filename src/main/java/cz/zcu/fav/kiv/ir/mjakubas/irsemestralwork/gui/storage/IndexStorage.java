package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.storage;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.IndexManager;

/**
 * Index storage for GUI purposes.
 *
 * @param titleManager Title index manger.
 * @param textManager  Text index manager.
 */
public record IndexStorage(IndexManager titleManager, IndexManager textManager) {
}
