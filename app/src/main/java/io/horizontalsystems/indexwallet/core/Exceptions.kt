package io.horizontalsystems.indexwallet.core

class UnsupportedAccountException : Exception()
class EosUnsupportedException : Exception()
class WrongAccountTypeForThisProvider : Exception()
class LocalizedException(val errorTextRes: Int) : Exception()
class InvalidMnemonicWordsCountException : Exception()
class AdapterErrorWrongParameters(override val message: String) : Exception()