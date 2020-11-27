cmake_minimum_required(VERSION 3.4.1)

project(lsMediaEditor)

# Build our own native library
add_library (lsMediaEditor SHARED oboe_audio.cpp )

# Find the Oboe package
find_package (oboe REQUIRED CONFIG)

# Specify the libraries which our native library is dependent on, including Oboe
target_link_libraries(lsMediaEditor log oboe::oboe)