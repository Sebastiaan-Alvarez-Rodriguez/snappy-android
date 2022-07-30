cmake_minimum_required(VERSION 3.18.1)

project("snappy_android")

function(dump_cmake_variables)
    get_cmake_property(_variableNames VARIABLES)
    list (SORT _variableNames)
    foreach (_variableName ${_variableNames})
        if (ARGV0)
            unset(MATCHED)
            string(REGEX MATCH ${ARGV0} MATCHED ${_variableName})
            if (NOT MATCHED)
                continue()
            endif()
        endif()
        message(STATUS "${_variableName}=${${_variableName}}")
    endforeach()
endfunction()

# Use below command to print all environment variables
# dump_cmake_variables()

# Adds snappy, the library we build a JNI bridge for
add_subdirectory(vendored/snappy)

add_subdirectory(android_snappy)