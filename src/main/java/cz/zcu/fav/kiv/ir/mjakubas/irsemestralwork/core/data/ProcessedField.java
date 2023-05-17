package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Record represents a processed document field. Processed record consists of preprocessed words.
 *
 * @param words           Preprocessed words.
 * @param wordFrequencies Words frequencies in a document.
 */
public record ProcessedField(ImmutableList<String> words, ImmutableMap<String, Integer> wordFrequencies) {
}
