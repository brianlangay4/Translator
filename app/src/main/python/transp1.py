# Make sure this code is in your transp2.py file
from deep_translator import GoogleTranslator

def translate_and_print(phrase, language):
    try:
        translated_phrase = GoogleTranslator(target=language).translate(phrase)
        return translated_phrase  # Return instead of print
    except Exception as e:
        return str(e)  # Return error message if any
