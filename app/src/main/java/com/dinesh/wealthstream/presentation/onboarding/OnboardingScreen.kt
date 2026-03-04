package com.dinesh.wealthstream.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinesh.wealthstream.ui.theme.*
import kotlinx.coroutines.launch

data class OnboardingPage(
    val emoji: String,
    val title: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            emoji = "📈",
            title = "Track Your Investments",
            description = "Monitor real-time stock prices and market trends in one place"
        ),
        OnboardingPage(
            emoji = "⭐",
            title = "Create Watchlists",
            description = "Save your favorite stocks and get instant updates on price changes"
        ),
        OnboardingPage(
            emoji = "🔔",
            title = "Price Alerts",
            description = "Set custom alerts and never miss important price movements"
        ),
        OnboardingPage(
            emoji = "💼",
            title = "Build Your Portfolio",
            description = "Track your investments and watch your wealth grow over time"
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Purple50, Blue50)
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Skip button
            if (pagerState.currentPage < pages.size - 1) {
                TextButton(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp)
                ) {
                    Text("Skip", color = Purple600)
                }
            } else {
                Spacer(modifier = Modifier.height(56.dp))
            }

            // Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageContent(pages[page])
            }

            // Page Indicators
            PageIndicators(
                pageCount = pages.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Navigation Button
            Button(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onGetStarted()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 48.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple600
                ),
                shape = CustomShapes.roundedCorner16
            ) {
                Text(
                    text = if (pagerState.currentPage < pages.size - 1) "Next" else "Get Started",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = page.emoji,
            fontSize = 120.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = page.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Gray900,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = page.description,
            fontSize = 16.sp,
            color = Gray600,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun PageIndicators(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(
                        width = if (index == currentPage) 24.dp else 8.dp,
                        height = 8.dp
                    )
                    .background(
                        color = if (index == currentPage) Purple600 else Gray300,
                        shape = CustomShapes.roundedCorner8
                    )
            )
        }
    }
}