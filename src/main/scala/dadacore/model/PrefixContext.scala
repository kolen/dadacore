package dadacore.model

import collection.immutable.LinearSeq

/**
 * Context with N prefix words. Usually used in n-gram models.
 *
 * @tparam Word
 */
class PrefixContext[Word](val word_list: Seq[Word]) extends Context  {

}
