package com.kykers.naplite.objects

import java.io.Serializable

/**
 * БЖУ с калориями.
 * @param title - объём, для которого произведен расчёт. Например: 100г или "Готового блюда"
 * @param calories - Количество калорий.
 * @param proteins - Количество белков.
 * @param fats - Количество жиров.
 * @param carbohydrates - Количество углеводов.
 *
 * @author goga133
 */
data class Composition(
    val title: String,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double
) : Serializable